package com.fml.blah.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fml.blah.auth.entity.ServerAuth;
import com.fml.blah.auth.mapper.ServerAuthMapper;
import com.fml.blah.auth.service.IServerAuthMbpService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author y
 * @since 2022-02-26
 */
@Service
public class ServerAuthMbpServiceImpl extends ServiceImpl<ServerAuthMapper, ServerAuth>
    implements IServerAuthMbpService {}
