package com.fml.blah.oauth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicKeyController {

  @Autowired private KeyPair keyPair;

  @GetMapping("/rsa/getPublicKey")
  public Map<String, Object> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAKey key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }
}
