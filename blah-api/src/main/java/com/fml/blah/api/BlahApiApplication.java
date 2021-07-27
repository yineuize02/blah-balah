package com.fml.blah.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients({"com.fml.blah.remote_interface", "com.fml.blah.feign_interceptor"})
@EnableDiscoveryClient
@SpringBootApplication
public class BlahApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(BlahApiApplication.class, args);
  }
}
