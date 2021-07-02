package com.fml.blah.common.redis;

import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class RedissonConfig {

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient() throws IOException {
    Config config = null;
    var yml = new ClassPathResource("redisson-config.yml");
    config = Config.fromYAML(yml.getFile());
    RedissonClient redisson = Redisson.create(config);
    return redisson;
  }

  //  @Bean
  //  CacheManager cacheManager(RedissonClient redissonClient) {
  //    Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
  //
  //    // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
  //    config.put("testMap", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
  //    config.put("testMap2", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
  //
  //    return new RedissonSpringCacheManager(redissonClient, config);
  //  }
}
