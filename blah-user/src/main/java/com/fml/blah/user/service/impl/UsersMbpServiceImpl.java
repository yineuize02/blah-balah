package com.fml.blah.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.mapper.UsersMapper;
import com.fml.blah.user.service.IUsersMbpService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author y
 * @since 2021-05-28
 */
@Service
public class UsersMbpServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements IUsersMbpService {}
