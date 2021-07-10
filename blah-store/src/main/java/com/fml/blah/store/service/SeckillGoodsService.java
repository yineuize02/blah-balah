package com.fml.blah.store.service;

import com.fml.blah.store.mapper_extend.SeckillGoodsExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillGoodsService {

  @Autowired private SeckillGoodsExtendsMapper seckillGoodsExtendsMapper;

  @Autowired private IOrderMbpService orderMbpService;

  @Transactional
  public void reduceStock(Integer seckillGoodsId) {
    seckillGoodsExtendsMapper.selectForUpdate(seckillGoodsId);
    seckillGoodsExtendsMapper.reduceStock(seckillGoodsId);
  }
}
