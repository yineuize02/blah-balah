package com.fml.blah.oauth.service;

import com.fml.blah.common.constants.ResponseMessageConstants;
import com.fml.blah.oauth.dto.BlahUserDetails;
import com.fml.blah.remote_interface.user.UserRemoteServiceInterface;
import com.fml.blah.remote_interface.user.dto.RoleDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlahUserDetailServiceImpl implements UserDetailsService {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRemoteServiceInterface userRemoteService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    var response = userRemoteService.getUserByName(username);
    log.warn(response.toString());
    if (ResponseMessageConstants.FALLBACK.equals(response.getMessage())) {
      throw new UsernameNotFoundException("FALLBACK");
    }

    var user = response.getData();
    if (user == null) {
      throw new UsernameNotFoundException("USERNAME_PASSWORD_ERROR");
    }

    var userAuthorities =
        Optional.ofNullable(user.getRoles())
            .map(
                l ->
                    l.stream()
                        .map(RoleDto::getName)
                        .map(s -> new SimpleGrantedAuthority(s))
                        .collect(Collectors.toList()))
            .orElse(List.of());
    var userDetail =
        BlahUserDetails.builder()
            .id(user.getId())
            .username(user.getUserName())
            .password(user.getPassword())
            .enabled(true)
            .authorities(userAuthorities)
            .build();
    return userDetail;
  }
}
