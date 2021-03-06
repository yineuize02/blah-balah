package com.fml.blah.common.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fml.blah.remote_interface.oauth.OauthRemoteServiceInterface;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.text.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthApplicationRunner implements ApplicationRunner {
  @Autowired private OauthRemoteServiceInterface oauthRemoteService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    setJwk();
  }

  private void setJwk() {

    var jwkSetMap = oauthRemoteService.getKey();
    ObjectMapper objectMapper = new ObjectMapper();
    String jwkStr = null;
    try {
      jwkStr = objectMapper.writeValueAsString(jwkSetMap);
    } catch (JsonProcessingException e) {
      log.error(e.toString());
      return;
    }

    JWKSet jwkSet = null;
    try {
      jwkSet = JWKSet.parse(jwkStr);
    } catch (ParseException e) {
      log.error(e.toString());
      return;
    }

    var rsaKey = (RSAKey) jwkSet.getKeys().get(0);
    if (rsaKey == null) {
      return;
    }

    try {
      var publicK = rsaKey.toRSAPublicKey();
      AuthConfig.getInstance().setJwtPublicKey(publicK);
    } catch (JOSEException e) {
      log.error(e.toString());
    }
  }
}
