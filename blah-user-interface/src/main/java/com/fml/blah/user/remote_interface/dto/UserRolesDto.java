package com.fml.blah.user.remote_interface.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRolesDto extends UserDto implements Serializable {

  private List<RoleDto> roles;
}
