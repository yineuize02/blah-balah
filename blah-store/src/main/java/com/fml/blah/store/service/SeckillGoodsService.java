package com.fml.blah.store.service;

import com.fml.blah.store.mapper_extend.SeckillGoodsExtendsMapper;
import com.fml.blah.store.rabbit.receiver.SeckillMessage;
import com.fml.blah.store.service.OrderService.OrderCreatePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillGoodsService {

  @Autowired private SeckillGoodsExtendsMapper seckillGoodsExtendsMapper;

  @Autowired private ISeckillGoodsMbpService seckillGoodsMbpService;
  @Autowired private OrderService orderService;

  @Transactional
  public void onSeckill(SeckillMessage msg) {
    var seckillGoodsId = msg.getSeckillGoodsId();

    var result = seckillGoodsExtendsMapper.reduceStock(seckillGoodsId, msg.getCount());
    if (result <= 0) {
      throw new RuntimeException(
          String.format("reduceStock %d %d result %d", seckillGoodsId, msg.getCount(), result));
    }

    var goods = seckillGoodsMbpService.getById(seckillGoodsId);

    var payload =
        OrderCreatePayload.builder()
            .count(msg.getCount())
            .goodsId(goods.getGoodsId())
            .createTime(msg.getCreateTime())
            .price(goods.getPrice())
            .userId(msg.getUserId())
            .build();

    orderService.createOrder(payload);
  }
}
