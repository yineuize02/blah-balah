package com.fml.blah.gateway.config;

import com.fml.blah.gateway.dto.BlahUserDetails;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/** @author yrz */
public class BlahAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

  private String token;
  private BlahUserDetails userDetail;
  /**
   * Creates a token with the supplied array of authorities.
   *
   * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented
   *     by this authentication object.
   */
  public BlahAuthenticationToken(
      String token,
      BlahUserDetails userDetail,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.token = token;
    this.userDetail = userDetail;
    super.setDetails(userDetail);
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return userDetail;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    token = null;
  }
}
