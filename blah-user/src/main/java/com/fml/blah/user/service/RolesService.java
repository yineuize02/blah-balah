package com.fml.blah.user.service;

import com.fml.blah.user.entity.Roles;
import com.fml.blah.user.entity.UsersRoles;
import com.fml.blah.user.service.UsersService.UserRoleCreateParam;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public interface RolesService {

  Roles createRole(RolesCreateParam param);

  UsersRoles createUserRoles(UserRoleCreateParam param);

  @Builder
  @Data
  class RolesCreateParam {
    @NotNull String name;
  }
}
