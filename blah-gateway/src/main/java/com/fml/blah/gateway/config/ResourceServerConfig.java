package com.fml.blah.gateway.config;

import com.fml.blah.gateway.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
// 注解需要使用@EnableWebFluxSecurity而非@EnableWebSecurity,因为SpringCloud Gateway基于WebFlux
@EnableWebFluxSecurity
public class ResourceServerConfig {
  @Autowired private AuthorizationManager authorizationManager;
  @Autowired private BlahServerAuthenticationEntryPoint blahServerAuthenticationEntryPoint;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    http.oauth2ResourceServer().authenticationEntryPoint(blahServerAuthenticationEntryPoint);
    http.authorizeExchange()
        //        .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(), String.class))
        //        .permitAll()
        .anyExchange()
        .access(authorizationManager)
        .and()
        // .exceptionHandling()
        //        .accessDeniedHandler(accessDeniedHandler()) // 处理未授权
        //        .authenticationEntryPoint(authenticationEntryPoint()) // 处理未认证
        // .and()
        .csrf()
        .disable();

    return http.build();
  }

  //  /**
  //   * 未授权
  //   *
  //   * @return
  //   */
  //  @Bean
  //  ServerAccessDeniedHandler accessDeniedHandler() {
  //    return (exchange, denied) -> {
  //      Mono<Void> mono =
  //          Mono.defer(() -> Mono.just(exchange.getResponse()))
  //              .flatMap(
  //                  response ->
  //                      WebUtils.writeFailedToResponse(response, ResultCode.ACCESS_UNAUTHORIZED));
  //      return mono;
  //    };
  //  }
  //
  //  /** token无效或者已过期自定义响应 */
  //  @Bean
  //  ServerAuthenticationEntryPoint authenticationEntryPoint() {
  //    return (exchange, e) -> {
  //      Mono<Void> mono =
  //          Mono.defer(() -> Mono.just(exchange.getResponse()))
  //              .flatMap(
  //                  response ->
  //                      WebUtils.writeFailedToResponse(
  //                          response, ResultCode.TOKEN_INVALID_OR_EXPIRED));
  //      return mono;
  //    };
  //  }

  /**
   * @linkhttps://blog.csdn.net/qq_24230139/article/details/105091273
   *     ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication 需要把jwt的Claim中的authorities加入
   *     方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
   */
  @Bean
  public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>>
      jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }
}
