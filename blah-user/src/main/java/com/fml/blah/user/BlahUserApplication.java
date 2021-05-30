package com.fml.blah.user;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.fml.blah.user")
public class BlahUserApplication {

  @PostConstruct
  public void init() {
    // NOTE: 这里强制设置为 UTC+8 的时区，和 rails 端保持一致
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
  }

  public static void main(String[] args) {
    SpringApplication.run(BlahUserApplication.class, args);
  }
}
