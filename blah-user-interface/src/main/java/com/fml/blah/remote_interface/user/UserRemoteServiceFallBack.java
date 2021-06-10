package com.fml.blah.remote_interface.user;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.user.dto.UserRolesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRemoteServiceFallBack implements UserRemoteServiceInterface {

  @Override
  public WebResponse<UserRolesDto> getUserByName(String userName) {
    log.error("getUserByName fallback userName: " + userName);
    return WebResponse.fallback(null);
  }
}
