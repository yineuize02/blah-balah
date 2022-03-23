package com.fml.blah.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author yrz
 */
@EnableWebFluxSecurity
public class WebfluxSecurityConfig {
  @Autowired private BlahServerAuthenticationEntryPoint blahServerAuthenticationEntryPoint;
  @Autowired private BlahAccessDeniedHandler blahAccessDeniedHandler;
  @Autowired private BlahAuthorizationManager authorizationManager;
  @Autowired private WhiteListConfig whiteListConfig;
  @Autowired private BlahSecurityContextRepository blahSecurityContextRepository;
  @Autowired private BlahReactiveAuthenticationManager blahReactiveAuthenticationManager;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
    httpSecurity
        .authenticationManager(blahReactiveAuthenticationManager)
        .securityContextRepository(blahSecurityContextRepository)
        .authorizeExchange(
            exchange ->
                exchange
                    .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(), String.class))
                    .permitAll()
                    .pathMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    .anyExchange()
                    .access(authorizationManager))
        .exceptionHandling()
        .authenticationEntryPoint(blahServerAuthenticationEntryPoint)
        .and()
        .exceptionHandling()
        .accessDeniedHandler(blahAccessDeniedHandler)
        .and()
        .csrf()
        .disable();

    return httpSecurity.build();
  }
}
