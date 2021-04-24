package com.fml.blah.gateway.config;

import com.fml.blah.gateway.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vo.WebResponse;

@Component
public class BlahServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

  @Override
  public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
    var webResponse = WebResponse.unauthorized(e.getMessage());
    var mono = WebUtils.ofMonoResponse(serverWebExchange.getResponse(), webResponse);
    return mono;
  }
}
