package com.fml.blah.seckill.service;

import cn.hutool.core.util.NumberUtil;
import com.fml.blah.common.configs.ratelimiter.RateLimiterConfig;
import com.fml.blah.common.exception.ServerErrorException;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.seckill.constants.RedisPrefix;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
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
    synchronized (this) {
      if (rateLimiter == null) {
        rateLimiter = rateLimiter.create(rate);
        seckillGoodsRateLimiterMap.put(seckillGoodsId, rateLimiter);
      }

      var oldRate = rateLimiter.getRate();
      if (NumberUtil.compare(rate, oldRate) != 0) {
        rateLimiter.setRate(rate);
      }
    }

    return rateLimiter.tryAcquire();
  }
}
