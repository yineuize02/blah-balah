package com.fml.blah.gateway.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fml.blah.common.dto.UserDetailDto;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.gateway.dto.BlahUserDetails;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author yrz
 */
@Component
public class BlahReactiveAuthenticationManager implements ReactiveAuthenticationManager {
  @Autowired private WebClient.Builder webBuilder;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {

    return Mono.just(authentication)
        .flatMap(
            auth -> {
              String token = auth.getCredentials().toString();
              return webBuilder
                  .baseUrl("lb://blah-auth/")
                  .build()
                  .get()
                  .uri(
                      uriBuilder ->
                          uriBuilder
                              .path("/auth/user/authentication")
                              .queryParam("token", token)
                              .build())
                  .header(HttpHeaders.AUTHORIZATION, token)
                  .retrieve()
                  .bodyToMono(WebResponse.class);
            })
        .map(
            response -> {
              if (!response.isSuccess()) {
                throw new BadCredentialsException("Invalid Credentials");
              }

              JSONObject jsonObject = JSONUtil.parseObj(response.getData());
              var data = jsonObject.toBean(UserDetailDto.class);
              //  var data = (UserDetailDto) response.getData();
              var userDetail = new BlahUserDetails();
              userDetail.setId(data.getId());
              userDetail.setUserName(data.getUserName());
              userDetail.setPassword(null);
              List<SimpleGrantedAuthority> authorities =
                  Optional.ofNullable(data.getRoles())
                      .map(
                          roles ->
                              roles.stream()
                                  .map(SimpleGrantedAuthority::new)
                                  .collect(Collectors.toList()))
                      .orElse(null);
              userDetail.setAuthorities(authorities);
              return new BlahAuthenticationToken(
                  authentication.getCredentials().toString(),
                  userDetail,
                  userDetail.getAuthorities());
            });
  }
}
