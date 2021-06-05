package com.fml.blah.oauth.service;

import com.fml.blah.common.constants.ResponseMessageConstants;
import com.fml.blah.oauth.dto.BlahUserDetails;
import com.fml.blah.user.remote_interface.UserRemoteServiceInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BlahUserDetailServiceImpl implements UserDetailsService {

  //  private List<BlahUserDetails> fooUserList;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRemoteServiceInterface userRemoteService;

  //  @PostConstruct
  //  public void initData() {
  //    var pass = passwordEncoder.encode("123456");
  //    fooUserList = new ArrayList<>();
  //    fooUserList.add(
  //        new BlahUserDetails(
  //            1L, "macro", pass, true, CollUtil.toList(new SimpleGrantedAuthority("ADMIN"))));
  //    fooUserList.add(
  //        new BlahUserDetails(
  //            2L, "andy", pass, true, CollUtil.toList(new SimpleGrantedAuthority("TEST"))));
  //  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    var response = userRemoteService.getUserByName(username);
    if (ResponseMessageConstants.FALLBACK.equals(response.getMessage())) {
      throw new UsernameNotFoundException("FALLBACK");
    }

    var user = response.getData();
    if (user == null) {
      throw new UsernameNotFoundException("USERNAME_PASSWORD_ERROR");
    }

    var userDetail = new BlahUserDetails();
    BeanUtils.copyProperties(user, userDetail);

    return userDetail;
  }
}
