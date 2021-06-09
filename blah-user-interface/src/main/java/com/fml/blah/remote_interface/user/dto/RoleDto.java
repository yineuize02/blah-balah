package com.fml.blah.remote_interface.user.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto implements Serializable {
  private Long id;
  private String name;
}
