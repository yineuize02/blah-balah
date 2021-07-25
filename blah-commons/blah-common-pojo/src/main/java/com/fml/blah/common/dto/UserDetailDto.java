package com.fml.blah.common.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDetailDto {
  private String userName;
  private Integer id;
  private List<String> authorities;
}
