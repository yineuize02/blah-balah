package com.fml.blah.remote_interface.user.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class UserRolesDto implements Serializable {
  private Long id;
  private String userName;
  private String password;
  private List<RoleDto> roles;
}
