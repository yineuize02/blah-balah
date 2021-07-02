package com.fml.blah.seckill.ratelimiter;

import org.springframework.stereotype.Component;

@Component
public class RateLimiterConfig {

  private int instanceCount;

  public void setInstanceCount(Integer instanceCount) {
    if (instanceCount == null || instanceCount.equals(0)) {
      instanceCount = 1;
    }

    this.instanceCount = instanceCount;
  }

  public int getInstanceCount() {

    return instanceCount;
  }
}
