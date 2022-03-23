package com.fml.blah.gateway.config;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.gateway.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/** @author yrz */
@Slf4j
@Component
public class BlahServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

  @Override
  public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
    return WebUtils.ofMonoResponse(
        exchange.getResponse(),
        WebResponse.unauthorized("Unauthorized EntryPoint" + e.getMessage()));
  }
}
