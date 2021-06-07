package com.fml.blah.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fml.blah.common.exception.ServerErrorException;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.entity.UsersRoles;
import com.fml.blah.user.entity_table_field_name.UsersFieldNames;
import com.fml.blah.user.mapper.RolesMapper;
import com.fml.blah.user.mapper.UsersRolesMapper;
import com.fml.blah.user.remote_interface.dto.RoleDto;
import com.fml.blah.user.remote_interface.dto.UserRolesDto;
import com.fml.blah.user.service.IUsersMbpService;
import com.fml.blah.user.service.UsersService;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired private IUsersMbpService usersMbpService;
  @Autowired private RolesMapper rolesMapper;
  @Autowired private UsersRolesMapper usersRolesMapper;
  @Autowired private PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public Users addUser(UserAddParam param) {
    var user = new Users();
    user.setUserName(param.getUserName());
    user.setPassword(passwordEncoder.encode(param.getPassword()));
    user.setCreatedAt(LocalDateTime.now());
    var succeed = usersMbpService.save(user);
    if (!succeed) {
      throw new ServerErrorException("usersMbpService.save fail user name:" + param.getUserName());
    }
    return user;
  }

  @Override
  public UserRolesDto getUserInfoByName(String userName) {
    var user =
        usersMbpService.getOne(new QueryWrapper<Users>().eq(UsersFieldNames.userName, userName));
    if (user == null) {
      return null;
    }

    var roleIds =
        usersRolesMapper
            .selectList(Wrappers.<UsersRoles>lambdaQuery().eq(UsersRoles::getUserId, user.getId()))
            .stream()
            .map(UsersRoles::getRoleId)
            .collect(Collectors.toSet());
    var roles = rolesMapper.selectBatchIds(roleIds);
    var roleDtoList =
        roles.stream()
            .map(
                r -> {
                  var d = new RoleDto();
                  d.setId(r.getId());
                  d.setName(r.getName());
                  return d;
                })
            .collect(Collectors.toList());
    var userDto = new UserRolesDto();
    BeanUtils.copyProperties(user, userDto);
    userDto.setRoles(roleDtoList);
    return userDto;
  }
}
