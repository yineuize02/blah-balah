package com.fml.blah.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author y
 * @since 2021-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private String name;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
