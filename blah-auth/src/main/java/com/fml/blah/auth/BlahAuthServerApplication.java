package com.fml.blah.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** @author yrz */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.fml.blah.auth")
@MapperScan({"com.fml.blah.auth.mapper", "com.fml.blah.auth.mapper_extend"})
public class BlahAuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlahAuthServerApplication.class, args);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
