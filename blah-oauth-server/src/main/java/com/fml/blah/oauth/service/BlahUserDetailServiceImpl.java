package com.fml.blah.oauth.service;

import cn.hutool.core.collection.CollUtil;
import com.fml.blah.oauth.dto.BlahUserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlahUserDetailServiceImpl implements UserDetailsService {

  private List<BlahUserDetails> fooUserList;

  @PostConstruct
  public void initData() {

    fooUserList = new ArrayList<>();
    fooUserList.add(
        new BlahUserDetails(
            1L, "macro", "password", true, CollUtil.toList(new SimpleGrantedAuthority("ADMIN"))));
    fooUserList.add(
        new BlahUserDetails(
            2L, "andy", "password", true, CollUtil.toList(new SimpleGrantedAuthority("TEST"))));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // todo replace
    List<BlahUserDetails> userDetails =
        fooUserList.stream()
            .filter(item -> item.getUsername().equals(username))
            .collect(Collectors.toList());
    if (CollUtil.isEmpty(userDetails)) {
      throw new UsernameNotFoundException("USERNAME_PASSWORD_ERROR");
    }

    return userDetails.get(0);
  }
}
