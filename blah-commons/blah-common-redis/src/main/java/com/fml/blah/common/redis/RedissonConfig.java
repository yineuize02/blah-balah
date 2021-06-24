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
}
