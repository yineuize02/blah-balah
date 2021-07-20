package com.fml.blah.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author y
 * @since 2021-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShopOrder implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /** 商品id */
  private Long goodsId;

  private Long userId;

  private LocalDateTime deleteTime;

  private LocalDateTime createTime;

  private LocalDateTime payTime;

  private Integer count;

  private BigDecimal price;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Boolean deleted;
}
