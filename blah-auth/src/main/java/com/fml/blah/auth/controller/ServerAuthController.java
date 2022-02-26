package com.fml.blah.auth.controller;

import com.fml.blah.auth.dto.ServerAuthPayload;
import com.fml.blah.auth.service.IServerAuthMbpService;
import com.fml.blah.common.vo.WebResponse;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author yrz */
@RestController
@RequestMapping("/auth/server")
public class ServerAuthController {

  @Autowired private IServerAuthMbpService serverAuthMbpService;
  @Autowired private KeyPair keyPair;

  @PostMapping("/authentication")
  public WebResponse<String> authentication(@RequestBody ServerAuthPayload payload) {

    return null;
  }

  @GetMapping("/rsa/getPublicKey")
  public Map<String, Object> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAKey key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }
}
