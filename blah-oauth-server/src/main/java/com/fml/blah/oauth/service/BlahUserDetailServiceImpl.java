package com.fml.blah.oauth.service;

import com.fml.blah.oauth.dto.BlahUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlahUserDetailServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    var user = new BlahUserDetails();
    user.setId(111L);
    user.setEnabled(true);
    user.setPassword("1111");
    user.setUsername(s);
    return user;
  }
}
