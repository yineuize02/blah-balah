package com.fml.blah.store.service;

import cn.hutool.core.bean.BeanUtil;
import com.fml.blah.store.entity.Order;
import com.fml.blah.store.rabbit.sender.CancelOrderSender;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  @Autowired private IOrderMbpService orderMbpService;
  @Autowired private CancelOrderSender cancelOrderSender;

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class OrderCreatePayload {
    private Long goodsId;
    private Long userId;
    private LocalDateTime createTime;
    private BigDecimal price;
    private Integer count;
  }

  @Transactional
  public void createOrder(OrderCreatePayload payload) {
    var order = new Order();
    BeanUtil.copyProperties(payload, order, false);
    order.setDeleted(false);

    var saved = orderMbpService.save(order);
    if (!saved) {
      throw new RuntimeException(
          String.format(
              "orderMbpService.save(order) fail goodsId %d userId %d",
              payload.getGoodsId(), payload.getUserId()));
    }

    cancelOrderSender.sendCancelOrderDelay(order.getId(), 1000 * 60 * 2L);
  }
}
