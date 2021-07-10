package com.fml.blah.user.security;

// import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @EnableWebSecurity
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//    http.authorizeRequests()
//        .requestMatchers(EndpointRequest.toAnyEndpoint())
//        .permitAll()
//        .and()
//        .authorizeRequests()
//        .anyRequest()
//        .authenticated()
//        .and()
//        .csrf()
//        .disable();
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }
// }
