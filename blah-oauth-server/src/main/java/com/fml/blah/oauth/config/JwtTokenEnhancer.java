package com.fml.blah.oauth.config;

import com.fml.blah.oauth.dto.BlahUserDetails;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    var userDetail = (BlahUserDetails) authentication.getPrincipal();
    Map<String, Object> info = Map.of("id", userDetail.getId());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
    return accessToken;
  }
}
