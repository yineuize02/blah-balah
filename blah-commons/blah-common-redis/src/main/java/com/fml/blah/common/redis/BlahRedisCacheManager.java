package com.fml.blah.common.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class BlahRedisCacheManager extends AbstractTransactionSupportingCacheManager {

  private final RedisCacheWriter cacheWriter;
  private final RedisCacheConfiguration defaultCacheConfig;
  private final Map<String, RedisCacheConfiguration> initialCacheConfiguration;
  private final boolean allowInFlightCacheCreation;

  /**
   * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default {@link
   * RedisCacheConfiguration}.
   *
   * @param cacheWriter must not be {@literal null}.
   * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use {@link
   *     RedisCacheConfiguration#defaultCacheConfig()}.
   * @param allowInFlightCacheCreation allow create unconfigured caches.
   * @since 2.0.4
   */
  private BlahRedisCacheManager(
      RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration,
      boolean allowInFlightCacheCreation) {

    Assert.notNull(cacheWriter, "CacheWriter must not be null!");
    Assert.notNull(defaultCacheConfiguration, "DefaultCacheConfiguration must not be null!");

    this.cacheWriter = cacheWriter;
    this.defaultCacheConfig = defaultCacheConfiguration;
    this.initialCacheConfiguration = new LinkedHashMap<>();
    this.allowInFlightCacheCreation = allowInFlightCacheCreation;
  }

  /**
   * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default {@link
   * RedisCacheConfiguration}.
   *
   * @param cacheWriter must not be {@literal null}.
   * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use {@link
   *     RedisCacheConfiguration#defaultCacheConfig()}.
   */
  public BlahRedisCacheManager(
      RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
    this(cacheWriter, defaultCacheConfiguration, true);
  }

  /**
   * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default {@link
   * RedisCacheConfiguration}.
   *
   * @param cacheWriter must not be {@literal null}.
   * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use {@link
   *     RedisCacheConfiguration#defaultCacheConfig()}.
   * @param initialCacheNames optional set of known cache names that will be created with given
   *     {@literal defaultCacheConfiguration}.
   */
  public BlahRedisCacheManager(
      RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration,
      String... initialCacheNames) {

    this(cacheWriter, defaultCacheConfiguration, true, initialCacheNames);
  }

  /**
   * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default {@link
   * RedisCacheConfiguration}.
   *
   * @param cacheWriter must not be {@literal null}.
   * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use {@link
   *     RedisCacheConfiguration#defaultCacheConfig()}.
   * @param allowInFlightCacheCreation if set to {@literal true} no new caches can be acquire at
   *     runtime but limited to the given list of initial cache names.
   * @param initialCacheNames optional set of known cache names that will be created with given
   *     {@literal defaultCacheConfiguration}.
   * @since 2.0.4
   */
  public BlahRedisCacheManager(
      RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration,
      boolean allowInFlightCacheCreation,
      String... initialCacheNames) {

    this(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation);

    for (String cacheName : initialCacheNames) {
      this.initialCacheConfiguration.put(cacheName, defaultCacheConfiguration);
    }
  }

  /**
   * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default {@link
   * RedisCacheConfiguration}.
   *
   * @param cacheWriter must not be {@literal null}.
   * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use {@link
   *     RedisCacheConfiguration#defaultCacheConfig()}.
   * @param initialCacheConfigurations Map of known cache names along with the configuration to use
   *     for those caches. Must not be {@literal null}.
   */
  public BlahRedisCacheManager(
      RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration,
      Map<String, RedisCacheConfiguration> initialCacheConfigurations) {

    this(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, true);
  }

  /**
   * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default {@link
   * RedisCacheConfiguration}.
   *
   * @param cacheWriter must not be {@literal null}.
   * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use {@link
   *     RedisCacheConfiguration#defaultCacheConfig()}.
   * @param initialCacheConfigurations Map of known cache names along with the configuration to use
   *     for those caches. Must not be {@literal null}.
   * @param allowInFlightCacheCreation if set to {@literal false} this cache manager is limited to
   *     the initial cache configurations and will not create new caches at runtime.
   * @since 2.0.4
   */
  public BlahRedisCacheManager(
      RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration,
      Map<String, RedisCacheConfiguration> initialCacheConfigurations,
      boolean allowInFlightCacheCreation) {

    this(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation);

    Assert.notNull(initialCacheConfigurations, "InitialCacheConfigurations must not be null!");

    this.initialCacheConfiguration.putAll(initialCacheConfigurations);
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.cache.support.AbstractCacheManager#loadCaches()
   */
  @Override
  protected Collection<BlahRedisCache> loadCaches() {

    List<BlahRedisCache> caches = new LinkedList<>();

    for (Map.Entry<String, RedisCacheConfiguration> entry : initialCacheConfiguration.entrySet()) {
      caches.add(createRedisCache(entry.getKey(), entry.getValue()));
    }

    return caches;
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.cache.support.AbstractCacheManager#getMissingCache(java.lang.String)
   */
  @Override
  protected BlahRedisCache getMissingCache(String name) {
    return allowInFlightCacheCreation ? createRedisCache(name, defaultCacheConfig) : null;
  }

  /**
   * @return unmodifiable {@link Map} containing cache name / configuration pairs. Never {@literal
   *     null}.
   */
  public Map<String, RedisCacheConfiguration> getCacheConfigurations() {

    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(getCacheNames().size());

    getCacheNames()
        .forEach(
            it -> {
              RedisCache cache = RedisCache.class.cast(lookupCache(it));
              configurationMap.put(it, cache != null ? cache.getCacheConfiguration() : null);
            });

    return Collections.unmodifiableMap(configurationMap);
  }

  protected BlahRedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
    String[] array = StringUtils.delimitedListToStringArray(name, "#");
    name = array[0];
    if (array.length > 1) { // 解析TTL
      long ttl = Long.parseLong(array[1]);
      cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl)); // 注意单位我此处用的是秒，而非毫秒
    }

    // 过期时间随机,防止大量的缓存key在同一时间失效造成缓存雪崩
    var isRandom = array.length > 2 && !StringUtils.isEmpty(array[2]);
    var upperBound = isRandom ? Long.parseLong(array[2]) * 1000 : 0L;

    return new BlahRedisCache(name, cacheWriter, cacheConfig, upperBound);
  }
}
