package com.fml.blah.seckill;

import com.fml.blah.common.zookeeper.EnableZookeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableZookeeper
@EnableDiscoveryClient
@SpringBootApplication
public class BlahSecKillApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlahSecKillApplication.class, args);
  }
}
