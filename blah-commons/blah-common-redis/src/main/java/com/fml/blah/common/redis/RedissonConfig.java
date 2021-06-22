package com.fml.blah.common.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
  @Value("${redisson.host}")
  private String host;

  @Value("${redisson.port}")
  private String port;

  @Value("${redisson2.host}")
  private String host2;

  @Value("${redisson2.port}")
  private String port2;

  @Value("${redisson3.host}")
  private String host3;

  @Value("${redisson3.port}")
  private String port3;

  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://" + host + ":" + port);
    RedissonClient redisson = Redisson.create(config);
    return redisson;
  }

  @Bean("redissonClient2")
  public RedissonClient redissonClient2() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://" + host2 + ":" + port2);
    RedissonClient redisson = Redisson.create(config);
    return redisson;
  }

  @Bean("redissonClient3")
  public RedissonClient redissonClient3() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://" + host3 + ":" + port3);
    RedissonClient redisson = Redisson.create(config);
    return redisson;
  }
}
