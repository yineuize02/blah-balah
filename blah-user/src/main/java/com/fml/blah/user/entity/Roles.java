package com.fml.blah.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author y
 * @since 2021-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Roles implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
