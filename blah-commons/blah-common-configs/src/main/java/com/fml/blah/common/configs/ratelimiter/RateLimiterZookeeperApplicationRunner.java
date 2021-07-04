package com.fml.blah.common.configs.ratelimiter;

import cn.hutool.core.net.NetUtil;
import com.fml.blah.common.zookeeper.ZookeeperUtils;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@ConditionalOnBean(name = "ZookeeperUtils")
@Slf4j
@Component
public class RateLimiterZookeeperApplicationRunner implements ApplicationRunner {

  @Autowired private ZookeeperUtils zookeeperUtils;
  @Autowired private CuratorFramework curatorFramework;
  @Autowired private RateLimiterConfig rateLimiterConfig;

  @Value("${spring.application.name}")
  private String serviceName;

  @Value("${blah.ratelimiter.distribute.byNacos:true}")
  private Boolean distributeByNacos;

  @Value("${blah.ratelimiter.distribute.byZookeeper:false}")
  private Boolean distributeByZookeeper;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (distributeByNacos || !distributeByZookeeper) {
      return;
    }
    var path = "/instances_" + serviceName;
    boolean isExist = Optional.ofNullable(zookeeperUtils.isExists(path)).orElse(false);
    if (!isExist) {
      isExist = zookeeperUtils.create(path);
    }

    if (!isExist) {
      log.error("create zookeeper path fail " + path);
    }

    var hostIp = Optional.of(NetUtil.getLocalhostStr()).orElse("unknow_ip");
    zookeeperUtils.createEphemeralSequential(path + "/" + hostIp, new byte[] {});

    var cache = CuratorCache.build(curatorFramework, path);
    CuratorCacheListener listener =
        CuratorCacheListener.builder()
            .forCreates(
                node -> {
                  log.info(String.format("Node created: [%s]", node));
                  refreshConfig(cache);
                })
            .forDeletes(
                oldNode -> {
                  log.info(String.format("Node deleted. Old value: [%s]", oldNode));
                  refreshConfig(cache);
                })
            .forInitialized(
                () -> {
                  log.info("Cache initialized");
                  refreshConfig(cache);
                })
            .build();

    // register the listener
    cache.listenable().addListener(listener);

    // the cache must be started
    cache.start();
  }

  private void refreshConfig(CuratorCache cache) {
    var count = cache.size();
    rateLimiterConfig.setInstanceCount(count);
  }
}
