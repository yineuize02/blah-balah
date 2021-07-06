package com.fml.blah.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fml.blah.store.entity.Order;
import com.fml.blah.store.mapper.OrderMapper;
import com.fml.blah.store.service.IOrderMbpService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author y
 * @since 2021-07-06
 */
@Service
public class OrderMbpServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements IOrderMbpService {}
