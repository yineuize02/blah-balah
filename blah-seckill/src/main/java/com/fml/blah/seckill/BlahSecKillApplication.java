package com.fml.blah.seckill;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BlahSecKillApplication {

  @NacosInjected private NamingService namingService;

  public static void main(String[] args) {
    SpringApplication.run(BlahSecKillApplication.class, args);
  }
}
