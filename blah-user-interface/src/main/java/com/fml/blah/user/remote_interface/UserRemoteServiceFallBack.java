package com.fml.blah.user.remote_interface;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.user.remote_interface.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRemoteServiceFallBack implements UserRemoteServiceInterface {

  @Override
  public WebResponse<UserDto> getUserByName(String userName) {
    log.error("getUserByName fallback userName: " + userName);
    return WebResponse.fallback(null);
  }
}
