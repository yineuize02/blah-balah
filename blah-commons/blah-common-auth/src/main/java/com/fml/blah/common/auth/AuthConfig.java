package com.fml.blah.common.auth;

import java.security.interfaces.RSAPublicKey;

public class AuthConfig {

  private static AuthConfig instance = new AuthConfig();

  private RSAPublicKey jwtPublicKey;

  public static AuthConfig getInstance() {
    return instance;
  }

  public void setJwtPublicKey(RSAPublicKey jwtPublicKey) {
    this.jwtPublicKey = jwtPublicKey;
  }

  public RSAPublicKey getJwtPublicKey() {
    return jwtPublicKey;
  }
}
