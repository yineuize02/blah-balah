package com.fml.blah.remote_interface.oauth;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OauthRemoteServiceFallBack implements OauthRemoteServiceInterface {

  @Override
  public Map<String, Object> getKey() {
    log.error("getKey fallback ");

    return Map.of();
  }
}
