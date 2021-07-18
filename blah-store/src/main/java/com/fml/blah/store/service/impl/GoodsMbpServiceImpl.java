package com.fml.blah.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fml.blah.store.entity.Goods;
import com.fml.blah.store.mapper.GoodsMapper;
import com.fml.blah.store.service.IGoodsMbpService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author y
 * @since 2021-07-18
 */
@Service
public class GoodsMbpServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements IGoodsMbpService {}
