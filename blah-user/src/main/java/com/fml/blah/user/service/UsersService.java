package com.fml.blah.user.service;

import com.fml.blah.user.entity.Users;
import com.fml.blah.user.remote_interface.dto.UserRolesDto;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface UsersService {

  Users addUser(UserAddParam param);

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
