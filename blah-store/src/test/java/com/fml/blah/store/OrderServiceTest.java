package com.fml.blah.store;

import com.fml.blah.store.entity.ShopOrder;
import com.fml.blah.store.mapper.ShopOrderMapper;
import com.fml.blah.test.ServiceTestBase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceTest extends ServiceTestBase {

  @Autowired private ShopOrderMapper shopOrderMapper;

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
}
