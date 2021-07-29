package com.fml.blah.seckill.rabbit.message;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeckillMessage {
  private Long userId;
  private Long seckillGoodsId;
  private LocalDateTime createTime;
  private Integer count;
}
