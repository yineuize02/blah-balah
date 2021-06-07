package com.fml.blah.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author y
 * @since 2021-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UsersRoles implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private Long userId;

  private Long roleId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
