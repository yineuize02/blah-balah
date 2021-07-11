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

  @Autowired private OrderService orderService;

  @Transactional
  public void onSeckill(SeckillMessage msg) {
    var seckillGoodsId = msg.getSeckillGoodsId();
    var list = seckillGoodsExtendsMapper.selectForUpdate(seckillGoodsId);
    if (list.size() == 0) {
      throw new RuntimeException("goods.size() == 0 seckillGoodsId");
    }

    var goods = list.get(0);
    seckillGoodsExtendsMapper.reduceStock(seckillGoodsId);
    var payload =
        OrderCreatePayload.builder()
            .count(1)
            .goodsId(goods.getGoodsId())
            .createTime(msg.getCreateTime())
            .price(goods.getPrice())
            .userId(msg.getUserId())
            .build();

    orderService.createOrder(payload);
  }
}
