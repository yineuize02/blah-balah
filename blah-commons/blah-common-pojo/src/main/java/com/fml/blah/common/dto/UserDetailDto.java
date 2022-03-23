package com.fml.blah.common.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDetailDto {
  private String userName;
  private Long id;
  private List<String> roles;
  // private List<String> authorities;
}
