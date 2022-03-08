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

  @Override
  public WebResponse<Boolean> checkPassword(String userName, String password) {
    log.error("checkPassword fallback userName: " + userName);
    return WebResponse.fallback(false);
  }

  @Override
  public WebResponse<UserRolesDto> createUser(UserAddParam param) {
    log.error("createUser fallback param: " + param.getUserName());
    return WebResponse.fallback(null);
  }
}
