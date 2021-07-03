package com.fml.blah.seckill.ratelimiter;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RateLimiterApplicationRunner implements ApplicationRunner {

  @Autowired private NacosServiceManager nacosServiceManager;
  @Autowired private NacosDiscoveryProperties nacosDiscoveryProperties;
  @Autowired private RateLimiterConfig rateLimiterConfig;

  @Value("${spring.application.name}")
  private String serviceName;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var namingService =
        nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
    var instances = namingService.getAllInstances(serviceName);
    rateLimiterConfig.setInstanceCount(instances.size());

    namingService.subscribe(
        serviceName,
        event -> {
          if (event instanceof NamingEvent) {

            log.info(((NamingEvent) event).getServiceName());

            try {
              var currentIns = namingService.getAllInstances(serviceName).size();
              rateLimiterConfig.setInstanceCount(currentIns);
            } catch (NacosException e) {
              log.error(e.getMessage());
            }
          }
        });
  }
}
