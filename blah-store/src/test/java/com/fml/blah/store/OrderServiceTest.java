package com.fml.blah.store;

import com.fml.blah.store.entity.SeckillGoods;
import com.fml.blah.store.entity.ShopOrder;
import com.fml.blah.store.mapper.SeckillGoodsMapper;
import com.fml.blah.store.mapper.ShopOrderMapper;
import com.fml.blah.store.mapper_extend.SeckillGoodsExtendsMapper;
import com.fml.blah.test.ServiceTestBase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceTest extends ServiceTestBase {

  @Autowired private ShopOrderMapper shopOrderMapper;

  @Autowired private SeckillGoodsMapper seckillGoodsMapper;
  @Autowired private SeckillGoodsExtendsMapper seckillGoodsExtendsMapper;

  @Test
  public void testDel() {
    var order = new ShopOrder();
    order.setDeleted(false);
    order.setCount(1);
    order.setCreateTime(LocalDateTime.now());
    order.setPrice(BigDecimal.ONE);
    order.setGoodsId(11L);
    order.setUserId(11L);
    shopOrderMapper.insert(order);

    var del = new ShopOrder();
    del.setId(order.getId());
    del.setDeleted(true);
    del.setPrice(BigDecimal.TEN);
    shopOrderMapper.updateById(del);

    var result = shopOrderMapper.selectById(order.getId());
    Assert.assertNotNull(result.getCount());
  }

  @Test
  public void testMapperReduceStock() {
    var seckillGoods = new SeckillGoods();
    seckillGoods.setGoodsId(999L);
    seckillGoods.setStock(999);
    seckillGoods.setStartTime(LocalDateTime.now());
    seckillGoods.setEndTime(LocalDateTime.now());
    seckillGoodsMapper.insert(seckillGoods);

    var result = seckillGoodsExtendsMapper.reduceStock(seckillGoods.getId(), 100);
    Assert.assertEquals(1, result);

    var s = seckillGoodsMapper.selectById(seckillGoods.getId());
    Assert.assertEquals(899, s.getStock().longValue());
  }
}
