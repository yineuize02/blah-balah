package com.fml.blah.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BlahGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(BlahGatewayApplication.class, args);
  }

  /** @return */
  @Bean
  @LoadBalanced
  public WebClient.Builder webBuilder() {
    return WebClient.builder();
  }
}
