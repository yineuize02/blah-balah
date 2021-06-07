package com.fml.blah.oauth.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
  @Autowired private JwtTokenEnhancer jwtTokenEnhancer;
  @Autowired private JwtAccessTokenConverter accessTokenConverter;
  @Autowired private PasswordEncoder passwordEncoder;

  @Qualifier("blahUserDetailServiceImpl")
  @Autowired
  public UserDetailsService blahUserDetailsService;

  @Autowired private AuthenticationManager authenticationManager;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    System.out.println(passwordEncoder.encode("123456"));
    clients
        .inMemory()
        .withClient("client-app")
        .secret(passwordEncoder.encode("123456"))
        .scopes("all")
        .authorizedGrantTypes("password", "refresh_token")
        .accessTokenValiditySeconds(3600 * 24)
        .refreshTokenValiditySeconds(3600 * 24 * 7);
    // 使用JdbcClientDetailsService客户端详情服务
    // clients.withClientDetails(new JdbcClientDetailsService(dataSource));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> delegates = new ArrayList<>();
    delegates.add(jwtTokenEnhancer);
    delegates.add(accessTokenConverter);
    enhancerChain.setTokenEnhancers(delegates); // 配置JWT的内容增强器
    endpoints
        .authenticationManager(authenticationManager)
        .userDetailsService(blahUserDetailsService) // 配置加载用户信息的服务
        .accessTokenConverter(accessTokenConverter)
        .tokenEnhancer(enhancerChain);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.allowFormAuthenticationForClients();
  }
}
