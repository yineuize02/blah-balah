package com.fml.blah.user.remote_interface.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto implements Serializable {
  private Long id;
  private String name;
}
