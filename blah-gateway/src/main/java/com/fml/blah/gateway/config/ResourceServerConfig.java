package com.fml.blah.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
// 注解需要使用@EnableWebFluxSecurity而非@EnableWebSecurity,因为SpringCloud Gateway基于WebFlux
// @EnableWebFluxSecurity
public class ResourceServerConfig {
  //  @Autowired private AuthorizationManager authorizationManager;
  //  @Autowired private BlahServerAuthenticationEntryPoint blahServerAuthenticationEntryPoint;
  //  @Autowired private BlahServerAccessDeniedHandler blahServerAccessDeniedHandler;
  //  @Autowired private WhiteListConfig whiteListConfig;

  // @Bean
  //  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
  //    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
  //    http.oauth2ResourceServer().authenticationEntryPoint(blahServerAuthenticationEntryPoint);
  //    http.authorizeExchange()
  //        .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(), String.class))
  //        .permitAll()
  //        .anyExchange()
  //        .access(authorizationManager)
  //        .and()
  //        .exceptionHandling()
  //        .accessDeniedHandler(blahServerAccessDeniedHandler) // 处理未授权
  //        .authenticationEntryPoint(blahServerAuthenticationEntryPoint) // 处理未认证
  //        .and()
  //        .csrf()
  //        .disable();
  //
  //    return http.build();
  //  }
  //
  //  /**
  //   * @linkhttps://blog.csdn.net/qq_24230139/article/details/105091273
  //   *     ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication 需要把jwt的Claim中的authorities加入
  //   *     方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
  //   */
  //  @Bean
  //  public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>>
  //      jwtAuthenticationConverter() {
  //    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
  //        new JwtGrantedAuthoritiesConverter();
  //    jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.ROLE_PREFIX);
  //
  // jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.AUTHORITIES_CLAIM_NAME);
  //
  //    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
  //
  // jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
  //    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  //  }
}
