package com.fml.blah.gateway.config;

import com.fml.blah.gateway.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vo.WebResponse;

@Component
public class BlahServerAccessDeniedHandler implements ServerAccessDeniedHandler {

  @Override
  public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
    var webResponse = WebResponse.unauthorized(e.getMessage());
    var mono = WebUtils.ofMonoResponse(serverWebExchange.getResponse(), webResponse);
    return mono;
  }
}
