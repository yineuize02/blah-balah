package com.fml.blah.remote_interface.store.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SeckillGoodsDto // implements Serializable
 {
  private Long id;

  /** 商品id */
  private Long goodsId;

  /** 库存 */
  private Integer stock;

  /** 价格 */
  private BigDecimal price;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
