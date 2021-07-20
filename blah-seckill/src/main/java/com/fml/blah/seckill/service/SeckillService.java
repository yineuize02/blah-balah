package com.fml.blah.seckill.service;

import cn.hutool.core.util.NumberUtil;
import com.fml.blah.common.configs.ratelimiter.RateLimiterConfig;
import com.fml.blah.common.exception.ServerErrorException;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.seckill.config.LuaConfig;
import com.fml.blah.seckill.constants.RedisPrefix;
import com.fml.blah.seckill.rabbit.SeckillSender;
import com.fml.blah.seckill.rabbit.message.SeckillMessage;
import com.google.common.util.concurrent.RateLimiter;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RScript.Mode;
import org.redisson.api.RScript.ReturnType;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
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
  @Autowired private LuaConfig luaConfig;

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

    RScript decrScript = redissonClient.getScript(StringCodec.INSTANCE);
    String evalResult =
        decrScript.eval(Mode.READ_WRITE, luaConfig.getPreDecrLua(), ReturnType.INTEGER);
    Integer decrResult = 0;
    try {
      decrResult = Integer.parseInt(evalResult);
    } catch (Exception e) {
      log.info("Integer.parseInt(evalResult) " + e.getMessage());
    }

    if (decrResult <= 0) {
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

    Integer finalFullStock = fullStock;

    seckillGoodsRateLimiterMap.computeIfAbsent(
        seckillGoodsId,
        key -> {
          double rate = (double) finalFullStock / rateLimiterConfig.getInstanceCount();
          return RateLimiter.create(rate);
        });

    var rateLimiter =
        seckillGoodsRateLimiterMap.computeIfPresent(
            seckillGoodsId,
            (key, value) -> {
              double rate = (double) finalFullStock / rateLimiterConfig.getInstanceCount();
              var oldRate = value.getRate();
              if (NumberUtil.compare(rate, oldRate) != 0) {
                value.setRate(rate);
              }

              return value;
            });

    return rateLimiter.tryAcquire();
  }
}
