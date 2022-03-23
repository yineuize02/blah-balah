package com.fml.blah.gateway.config;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yrz
 */
@Component
public class BlahSecurityContextRepository implements ServerSecurityContextRepository {
  public static final String TOKEN_HEADER = "Authorization";

  @Autowired private BlahReactiveAuthenticationManager blahReactiveAuthenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    List<String> headers = request.getHeaders().get(TOKEN_HEADER);
    if (CollectionUtils.isEmpty(headers)) {
      return Mono.empty();
    }

    String authorization = headers.get(0);
    if (StringUtils.isNotEmpty(authorization)) {
      return blahReactiveAuthenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(authorization, authorization))
          .map(SecurityContextImpl::new);
    }

    return Mono.empty();
  }
}
