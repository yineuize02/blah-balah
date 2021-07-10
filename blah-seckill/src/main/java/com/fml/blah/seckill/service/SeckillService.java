package com.fml.blah.seckill.service;

import cn.hutool.core.util.NumberUtil;
import com.fml.blah.common.configs.ratelimiter.RateLimiterConfig;
import com.fml.blah.common.exception.ServerErrorException;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.seckill.constants.RedisPrefix;
import com.fml.blah.seckill.rabbit.SeckillSender;
import com.fml.blah.seckill.rabbit.message.SeckillMessage;
import com.google.common.util.concurrent.RateLimiter;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SeckillService {

  private ConcurrentHashMap<Long, RateLimiter> seckillGoodsRateLimiterMap =
      new ConcurrentHashMap<>();

  private ConcurrentHashMap<Long, Integer> seckillGoodsFullStockMap = new ConcurrentHashMap<>();

  @Autowired private RateLimiterConfig rateLimiterConfig;
  @Autowired private RedisUtils redisUtils;
  @Autowired private RedissonClient redissonClient;
  @Autowired private SeckillSender seckillSender;

  public boolean doSeckill(Long seckillGoodsId, Long userId) {
    var acquireRateLimit = tryAcquireRateLimit(seckillGoodsId);
    if (!acquireRateLimit) {
      return false;
    }

    var userLock = redissonClient.getLock(RedisPrefix.SECKILL_USER_LOCK + userId);
    userLock.lock(1, TimeUnit.MINUTES);
    var userBuyCount =
        (Long) redisUtils.get(RedisPrefix.SECKILL_GOODS_USER_COUNT + seckillGoodsId + ":" + userId);
    if (userBuyCount != null && userBuyCount > 0) {
      userLock.unlock();
      return false;
    }

    userLock.unlockAsync();
    var decr = redisUtils.decr(RedisPrefix.SECKILL_STOCK + seckillGoodsId, 1);
    if (decr < 0) {
      return false;
    }

    log.info(String.format("user %d , seckillGoodsId %d 预扣库存成功", userId, seckillGoodsId));

    var msg =
        SeckillMessage.builder()
            .createTime(LocalDateTime.now())
            .userId(userId)
            .seckillGoodsId(seckillGoodsId)
            .build();
    seckillSender.send(msg);

    return true;
  }

  public boolean tryAcquireRateLimit(Long seckillGoodsId) {
    var fullStock = seckillGoodsFullStockMap.get(seckillGoodsId);
    if (fullStock == null) {

      fullStock = (Integer) redisUtils.get(RedisPrefix.SECKILL_FULL_STOCK + seckillGoodsId);
      if (fullStock == null) {
        throw new ServerErrorException("tryAcquireRateLimit full stock null, id " + seckillGoodsId);
      }

      seckillGoodsFullStockMap.put(seckillGoodsId, fullStock);
    }

    double rate = (double) fullStock / rateLimiterConfig.getInstanceCount();

    RateLimiter rateLimiter = seckillGoodsRateLimiterMap.get(seckillGoodsId);
    if (rateLimiter == null) {
      synchronized (this) {
        rateLimiter = seckillGoodsRateLimiterMap.get(seckillGoodsId);
        if (rateLimiter == null) {
          rateLimiter = rateLimiter.create(rate);
          seckillGoodsRateLimiterMap.put(seckillGoodsId, rateLimiter);
        }
      }
    }

    synchronized (this) {
      var oldRate = rateLimiter.getRate();
      if (NumberUtil.compare(rate, oldRate) != 0) {
        rateLimiter.setRate(rate);
      }
    }

    return rateLimiter.tryAcquire();
  }
}
