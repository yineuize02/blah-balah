package com.fml.blah.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableFeignClients("com.fml.blah.remote_interface")
@EnableAuthorizationServer
@EnableDiscoveryClient
@SpringBootApplication
public class BlahOauthServerApplication {

  public static void main(String[] args) {
    var applicationContext = SpringApplication.run(BlahOauthServerApplication.class, args);
    String datasourceUrl = applicationContext.getEnvironment().getProperty("spring.datasource.url");
    System.err.println(" spring.datasource.url: " + datasourceUrl);
    String shareConfig1 = applicationContext.getEnvironment().getProperty("share.config1");
    System.err.println(" share.config1: " + shareConfig1);
  }
}
