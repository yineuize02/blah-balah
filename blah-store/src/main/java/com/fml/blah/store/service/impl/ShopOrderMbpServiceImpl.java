package com.fml.blah.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fml.blah.store.entity.ShopOrder;
import com.fml.blah.store.mapper.ShopOrderMapper;
import com.fml.blah.store.service.IShopOrderMbpService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author y
 * @since 2021-07-18
 */
@Service
public class ShopOrderMbpServiceImpl extends ServiceImpl<ShopOrderMapper, ShopOrder>
    implements IShopOrderMbpService {}
