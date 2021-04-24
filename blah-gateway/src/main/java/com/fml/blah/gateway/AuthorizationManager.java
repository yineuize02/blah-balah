package com.fml.blah.gateway;

import cn.hutool.core.convert.Convert;
import com.fml.blah.common.constants.AuthConstants;
import com.fml.blah.common.constants.RedisConstants;
import com.fml.blah.gateway.config.WhiteListConfig;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
  @Autowired private RedisTemplate redisTemplate;
  @Autowired private WhiteListConfig whiteListConfig;

  @Override
  public Mono<AuthorizationDecision> check(
      Mono<Authentication> authentication, AuthorizationContext authorizationContext) {

    ServerHttpRequest request = authorizationContext.getExchange().getRequest();
    // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14396990.html
    String restPath = request.getMethodValue() + "_" + request.getURI().getPath();
    log.info("请求路径：{}", restPath);

    PathMatcher pathMatcher = new AntPathMatcher();
    // 对应跨域的预检请求直接放行
    if (request.getMethod() == HttpMethod.OPTIONS) {
      return Mono.just(new AuthorizationDecision(true));
    }

    // 白名单路径直接放行
    List<String> ignoreUrls = whiteListConfig.getUrls();
    URI uri = request.getURI();
    for (String ignoreUrl : ignoreUrls) {
      if (pathMatcher.match(ignoreUrl, uri.getPath())) {
        return Mono.just(new AuthorizationDecision(true));
      }
    }

    // 从缓存取资源权限角色关系列表
    Map<Object, Object> permissionRoles =
        redisTemplate.opsForHash().entries(RedisConstants.RESOURCE_ROLES_MAP);
    Iterator<Object> iterator = permissionRoles.keySet().iterator();
    // 请求路径匹配到的资源需要的角色权限集合authorities统计
    Set<String> authorities = new HashSet<>();
    while (iterator.hasNext()) {
      String pattern = (String) iterator.next();
      if (pathMatcher.match(pattern, restPath)) {
        authorities.addAll(Convert.toList(String.class, permissionRoles.get(pattern)));
      }
    }

    authorities =
        authorities.stream().map(i -> AuthConstants.ROLE_PREFIX + i).collect(Collectors.toSet());

    // roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
    Mono<AuthorizationDecision> authorizationDecisionMono =
        authentication
            .filter(Authentication::isAuthenticated)
            .flatMapIterable(Authentication::getAuthorities)
            .map(GrantedAuthority::getAuthority)
            .any(authorities::contains)
            .map(AuthorizationDecision::new)
            .defaultIfEmpty(new AuthorizationDecision(false));
    return authorizationDecisionMono;
    //  return Mono.just(new AuthorizationDecision(true));
  }
}
