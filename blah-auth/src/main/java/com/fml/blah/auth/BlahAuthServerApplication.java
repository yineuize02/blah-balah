package com.fml.blah.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** @author yrz */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.fml.blah.auth")
@MapperScan({"com.fml.blah.auth.mapper", "com.fml.blah.auth.mapper_extend"})
public class BlahAuthServerApplication {

  public static void main(String[] args) {
    var applicationContext = SpringApplication.run(BlahAuthServerApplication.class, args);
  }
}
