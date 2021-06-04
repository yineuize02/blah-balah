package com.fml.blah.user.remote_interface.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto implements Serializable {

  private Long id;

  private String userName;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
