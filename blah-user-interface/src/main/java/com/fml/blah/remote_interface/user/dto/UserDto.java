package com.fml.blah.remote_interface.user.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDto implements Serializable {
  private Long id;
  private String userName;
  private String password;
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
