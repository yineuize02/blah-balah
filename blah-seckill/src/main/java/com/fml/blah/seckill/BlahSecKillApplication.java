package com.fml.blah.seckill;

import com.fml.blah.common.zookeeper.EnableZookeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients({"com.fml.blah.remote_interface", "com.fml.blah.feign_interceptor"})
@EnableZookeeper
@EnableDiscoveryClient
@SpringBootApplication // (scanBasePackages = {"com.fml.blah.seckill",
                       // "com.fml.blah.remote_interface"})
public class BlahSecKillApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlahSecKillApplication.class, args);
  }
}
