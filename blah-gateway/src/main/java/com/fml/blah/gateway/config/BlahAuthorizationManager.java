package com.fml.blah.gateway.config;

import cn.hutool.json.JSONUtil;
import com.fml.blah.gateway.dto.BlahUserDetails;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author yrz
 */
@Component
public class BlahAuthorizationManager
    implements ReactiveAuthorizationManager<AuthorizationContext> {

  @Override
  public Mono<AuthorizationDecision> check(
      Mono<Authentication> authentication, AuthorizationContext object) {
    // todo: check if user is allowed to access the resource

    return authentication.map(
        auth -> {
          BlahUserDetails details = (BlahUserDetails) auth.getDetails();
          ServerHttpRequest request = object.getExchange().getRequest();
          String json = JSONUtil.toJsonStr(details);
          request.mutate().header("user", json).build();
          return new AuthorizationDecision(true);
        });
  }

  @Override
  public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
    return ReactiveAuthorizationManager.super.verify(authentication, object);
  }
}
