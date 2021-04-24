package com.fml.blah.oauth.controller;

import com.fml.blah.common.vo.WebResponse;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class AuthController {
  @Autowired private TokenEndpoint tokenEndpoint;
  @Autowired private KeyPair keyPair;

  @PostMapping("/token")
  public WebResponse<OAuth2AccessToken> postAccessToken(
      Principal principal, @RequestParam Map<String, String> parameters)
      throws HttpRequestMethodNotSupportedException {

    var oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
    return WebResponse.ok(oAuth2AccessToken);
  }

  @GetMapping("/rsa/getPublicKey")
  public Map<String, Object> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAKey key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }
}
