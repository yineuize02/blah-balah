package com.fml.blah.user.service;

import com.fml.blah.remote_interface.user.dto.UserRolesDto;
import com.fml.blah.user.entity.Users;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface UsersService {

  Users addUser(UserAddParam param);

  Boolean checkPassword(String userName, String password);

  UserRolesDto getUserInfoByName(String userName);

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Data
  class UserAddParam {
    @NotNull private String userName;
    @NotNull private String password;
  }

  @Data
  class UserRoleCreateParam {
    @NotNull private Long userId;
    @NotNull private Long roleId;
  }
}
