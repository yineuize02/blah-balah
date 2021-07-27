package com.fml.blah.common.auth;

import com.fml.blah.common.dto.UserDetailDto;

public class UserContext {
  private static ThreadLocal<UserDetailDto> userHolder = new ThreadLocal<UserDetailDto>();

  public static void setUser(UserDetailDto user) {
    userHolder.set(user);
  }

  public static UserDetailDto currentUser() {
    return userHolder.get();
  }

  public static void clear() {
    userHolder.remove();
  }
}
