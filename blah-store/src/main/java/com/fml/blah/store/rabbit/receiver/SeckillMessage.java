package com.fml.blah.store.rabbit.receiver;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SeckillMessage {
  private Long userId;
  private Long seckillGoodsId;
  private Integer count;
  private LocalDateTime createTime;
}
