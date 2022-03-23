package com.fml.blah.gateway.config;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.gateway.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yrz
 */
@Slf4j
@Component
public class BlahAccessDeniedHandler implements ServerAccessDeniedHandler {

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
    return WebUtils.ofMonoResponse(
        exchange.getResponse(), WebResponse.unauthorized("DeniedHandler " + denied.getMessage()));
  }
}
