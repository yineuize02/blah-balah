package com.fml.blah.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.gateway.config.WhiteListConfig;
import com.fml.blah.gateway.utils.WebUtils;
import java.net.URI;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

  private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);
  @Autowired private WhiteListConfig whiteListConfig;
  @Autowired WebClient.Builder webBuilder;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var request = exchange.getRequest();
    var path = request.getPath().toString();
    if (request.getMethod() == HttpMethod.OPTIONS) {
      return chain.filter(exchange);
    }

    List<String> ignoreUrls = whiteListConfig.getUrls();
    PathMatcher pathMatcher = new AntPathMatcher();
    URI uri = request.getURI();
    for (String ignoreUrl : ignoreUrls) {
      if (pathMatcher.match(ignoreUrl, uri.getPath())) {
        return chain.filter(exchange);
      }
    }

    String token = exchange.getRequest().getHeaders().getFirst("Authorization");

    if (StrUtil.isEmpty(token)) {
      return WebUtils.ofMonoResponse(
          exchange.getResponse(), WebResponse.unauthorized("token is empty"));
    }

    Mono mono =
        webBuilder
            .baseUrl("lb://blah-auth/")
            .build()
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder.path("/auth/user/authentication").queryParam("token", token).build())
            .header(HttpHeaders.AUTHORIZATION, token)
            .retrieve()
            .bodyToMono(WebResponse.class)
            .flatMap(
                response -> {
                  if (!response.isSuccess()) {
                    return WebUtils.ofMonoResponse(
                        exchange.getResponse(), WebResponse.unauthorized("token is invalid"));
                  }

                  return chain.filter(exchange);
                });

    //    try {
    //      // 从token中解析用户信息并设置到Header中去
    //      String realToken = token.replace("Bearer ", "");
    //      JWSObject jwsObject = JWSObject.parse(realToken);
    //      String userStr = jwsObject.getPayload().toString();
    //      LOGGER.info("AuthGlobalFilter.filter() user:{}", userStr);
    //      ServerHttpRequest request1 = exchange.getRequest().mutate().header("user",
    // userStr).build();
    //      exchange = exchange.mutate().request(request1).build();
    //    } catch (ParseException e) {
    //      e.printStackTrace();
    //    }
    return mono;
  }

  @Override
  public int getOrder() {
    return -1;
  }
}
