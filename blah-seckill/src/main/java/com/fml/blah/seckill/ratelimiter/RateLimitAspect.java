package com.fml.blah.seckill.ratelimiter;

import cn.hutool.core.util.NumberUtil;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RateLimitAspect {

  @Autowired private RateLimiterConfig rateLimiterConfig;

  private ConcurrentHashMap<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

  @Pointcut("@annotation(com.fml.blah.seckill.ratelimiter.Limiting)")
  public void serviceLimit() {}

  @Around("serviceLimit()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    // 获取拦截的方法名
    Signature sig = point.getSignature();
    // 获取拦截的方法名
    MethodSignature msig = (MethodSignature) sig;
    // 返回被织入增加处理目标对象
    Object target = point.getTarget();
    // 为了获取注解信息
    Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    // 获取注解信息
    Limiting annotation = currentMethod.getAnnotation(Limiting.class);
    double rate =
        (double) annotation.limitNum() / rateLimiterConfig.getInstanceCount(); // 获取注解每秒加入桶中的token
    String name =
        annotation.name().length() != 0 ? annotation.name() : msig.getName(); // 注解所在方法名区分不同的限流策略

    boolean isBLock = annotation.block();

    RateLimiter rateLimiter = rateLimiterMap.get(name);
    synchronized (this) {
      if (rateLimiter == null) {
        rateLimiter = rateLimiter.create(rate);
        rateLimiterMap.put(name, rateLimiter);
      }

      var oldRate = rateLimiter.getRate();
      if (NumberUtil.compare(rate, oldRate) != 0) {
        rateLimiter.setRate(rate);
      }
    }

    //    if (RATE_LIMITER.containsKey(name)) {
    //      rateLimiter = RATE_LIMITER.get(name);
    //    } else {
    //      RATE_LIMITER.put(name, RateLimiter.create(limitNum));
    //      rateLimiter = RATE_LIMITER.get(name);
    //    }
    if (rateLimiter.tryAcquire()) {
      log.info("处理完成");
      return point.proceed();
    } else {
      throw new RuntimeException("服务器繁忙，请稍后再试。");
    }
  }
}
