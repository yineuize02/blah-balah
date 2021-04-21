package com.fml.blah.oauth.config;

import java.security.KeyPair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

@Configuration
public class JwtTokenConfig {

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setKeyPair(keyPair());
    return jwtAccessTokenConverter;
  }

  @Bean
  public TokenStore jwtTokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  /** 从classpath下的密钥库中获取密钥对(公钥+私钥) */
  @Bean
  public KeyPair keyPair() {
    KeyStoreKeyFactory factory =
        new KeyStoreKeyFactory(new ClassPathResource("blah.jks"), "123456".toCharArray());
    KeyPair keyPair = factory.getKeyPair("blah", "123456".toCharArray());
    return keyPair;
  }
}
