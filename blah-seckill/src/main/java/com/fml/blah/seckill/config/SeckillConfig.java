package com.fml.blah.seckill.config;

import org.springframework.stereotype.Component;

@Component
public class SeckillConfig {

  private String preDecrLua;

  public String getPreDecrLua() {
    return preDecrLua;
  }

  public void setPreDecrLua(String lua) {
    preDecrLua = lua;
  }
}
