package com.fml.blah.user.service;

import com.fml.blah.user.entity.Roles;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public interface RolesService {

  Roles createRole(RolesCreateParam param);

  @Builder
  @Data
  class RolesCreateParam {
    @NotNull String name;
  }
}
