package com.fml.blah.user.service;

import com.fml.blah.user.entity.Users;
import com.fml.blah.user.remote_interface.dto.UserRolesDto;
import javax.validation.constraints.NotNull;
import lombok.Data;

public interface UsersService {

  Users addUser(UserAddParam param);

  UserRolesDto getUserInfoByName(String userName);

  @Data
  class UserAddParam {
    @NotNull private String userName;
    @NotNull private String password;
  }

  class UserRoleCreateParam {
    @NotNull private Long userId;
    @NotNull private Long roleId;
  }
}
