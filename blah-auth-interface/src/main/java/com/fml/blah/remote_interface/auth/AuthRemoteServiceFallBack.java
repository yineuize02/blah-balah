package com.fml.blah.remote_interface.auth;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.auth.dto.ServerAuthPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** @author yrz */
@Slf4j
@Component
public class AuthRemoteServiceFallBack implements AuthRemoteServiceInterface {

  @Override
  public WebResponse<String> serverAuthentication(ServerAuthPayload payload) {
    log.error("serverAuthentication fallback ");
    return WebResponse.fallback("serverAuthentication fallback");
  }
}
