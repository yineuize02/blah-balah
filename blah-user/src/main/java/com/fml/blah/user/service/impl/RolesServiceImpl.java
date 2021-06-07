package com.fml.blah.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fml.blah.common.exception.ServerErrorException;
import com.fml.blah.user.entity.Roles;
import com.fml.blah.user.entity.UsersRoles;
import com.fml.blah.user.mapper.RolesMapper;
import com.fml.blah.user.service.RolesService;
import com.fml.blah.user.service.UsersService.UserRoleCreateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolesServiceImpl implements RolesService {
  @Autowired private RolesMapper rolesMapper;

  @Transactional
  @Override
  public Roles createRole(RolesCreateParam param) {

    if (StrUtil.isBlank(param.getName())) {}

    var role = new Roles();
    role.setName(param.getName());
    var count = rolesMapper.insert(role);
    if (count == 0) {
      throw new ServerErrorException("rolesMapper.insert faild rolename: " + param.getName());
    }

    //    var result =
    //        rolesMapper.selectOne(Wrappers.<Roles>lambdaQuery().eq(Roles::getName,
    // param.getName()));

    return role;
  }

  public UsersRoles createUserRoles(UserRoleCreateParam param) {

    return null;
  }
}
