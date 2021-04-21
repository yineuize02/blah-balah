package com.fml.blah.oauth.controller;

import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth")
public class AuthController {
  @Autowired private TokenEndpoint tokenEndpoint;

  @PostMapping("/token")
  public OAuth2AccessToken postAccessToken(
      Principal principal, @RequestParam Map<String, String> parameters)
      throws HttpRequestMethodNotSupportedException {

    var oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
    return oAuth2AccessToken;
  }
}
