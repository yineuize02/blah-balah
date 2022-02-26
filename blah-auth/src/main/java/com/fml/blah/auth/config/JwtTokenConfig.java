package com.fml.blah.auth.config;

import java.security.KeyPair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

@Configuration
public class JwtTokenConfig {

  /** 从classpath下的密钥库中获取密钥对(公钥+私钥) */
  @Bean
  public KeyPair keyPair() {
    KeyStoreKeyFactory factory =
        new KeyStoreKeyFactory(new ClassPathResource("blah.jks"), "123456".toCharArray());
    return factory.getKeyPair("blah", "123456".toCharArray());
  }
}
