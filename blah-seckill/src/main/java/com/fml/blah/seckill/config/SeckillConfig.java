package com.fml.blah.seckill.config;

import java.security.interfaces.RSAPublicKey;
import org.springframework.stereotype.Component;

@Component
public class SeckillConfig {

  private String preDecrLua;

  private RSAPublicKey jwtPublicKey;

  public String getPreDecrLua() {
    return preDecrLua;
  }

  public void setPreDecrLua(String lua) {
    preDecrLua = lua;
  }

  public RSAPublicKey getJwtPublicKey() {
    return jwtPublicKey;
  }

  public void setJwtPublicKey(RSAPublicKey jwtPublicKey) {
    this.jwtPublicKey = jwtPublicKey;
  }
}
