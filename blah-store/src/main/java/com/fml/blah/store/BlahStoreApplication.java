package com.fml.blah.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.fml.blah.store.mapper", "com.fml.blah.store.mapper_extend"})
@SpringBootApplication
public class BlahStoreApplication {
  public static void main(String[] args) {
    SpringApplication.run(BlahStoreApplication.class, args);
  }
}
