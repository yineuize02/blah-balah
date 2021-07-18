package com.fml.blah.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fml.blah.store.entity.SeckillGoods;
import com.fml.blah.store.mapper.SeckillGoodsMapper;
import com.fml.blah.store.service.ISeckillGoodsMbpService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author y
 * @since 2021-07-18
 */
@Service
public class SeckillGoodsMbpServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods>
    implements ISeckillGoodsMbpService {}
