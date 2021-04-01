package com.fml.blah.oauth;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

@Configuration
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
  @Autowired private DataSource dataSource;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // 使用JdbcClientDetailsService客户端详情服务
    clients.withClientDetails(new JdbcClientDetailsService(dataSource));
  }
}
