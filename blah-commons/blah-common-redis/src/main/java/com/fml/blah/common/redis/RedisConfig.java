package com.fml.blah.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@AutoConfigureBefore(RedisTemplate.class)
@Configuration
public class RedisConfig {

  @Autowired private RedisSerializer<Object> redisSerializer;
  @Autowired private RedisTemplate<String, Object> redisTemplate;

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisSerializer<Object> serializer = redisSerializer;
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(serializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public RedisSerializer<Object> redisSerializer() {
    // 创建JSON序列化器
    Jackson2JsonRedisSerializer<Object> serializer =
        new Jackson2JsonRedisSerializer<>(Object.class);

    var codec = new JsonRedisCodec();
    var objectMapper = codec.getObjectMapper();

    serializer.setObjectMapper(objectMapper);
    return serializer;
  }

  //  @Bean
  //  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
  //    RedisCacheWriter redisCacheWriter =
  //        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
  //    // 设置Redis缓存有效期为1天
  //    RedisCacheConfiguration redisCacheConfiguration =
  //        RedisCacheConfiguration.defaultCacheConfig()
  //            .serializeValuesWith(
  //                RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
  //            .entryTtl(Duration.ofDays(1));
  //    return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
  //  }
  //
  //  @Bean
  //  CacheManager cacheManager(RedissonClient redissonClient) {
  //    Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
  //
  //    // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
  //    config.put("testMap", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
  //    return new RedissonSpringCacheManager(redissonClient, config);
  //  }
  @Bean
  public RedisUtils redisUtils() {
    return new RedisUtils(redisTemplate);
  }
}
