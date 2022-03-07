package com.fml.blah.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fml.blah.common.exception.ServerErrorException;
import com.fml.blah.remote_interface.user.dto.RoleDto;
import com.fml.blah.remote_interface.user.dto.UserRolesDto;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.entity.UsersRoles;
import com.fml.blah.user.mapper.RolesMapper;
import com.fml.blah.user.mapper.UsersRolesMapper;
import com.fml.blah.user.service.IUsersMbpService;
import com.fml.blah.user.service.UsersService;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired private IUsersMbpService usersMbpService;
  @Autowired private RolesMapper rolesMapper;
  @Autowired private UsersRolesMapper usersRolesMapper;
  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private UsersService usersService;

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

  @Cacheable("user:checkPassword#200#100")
  @Override
  public Boolean checkPassword(String userName, String password) {
    var user =
        usersMbpService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getUserName, userName));
    if (user == null) {
      return false;
    }
    return passwordEncoder.matches(password, user.getPassword());
  }

  // 过期时间3600秒加随机0到100秒
  @Cacheable("user:userinfo#3600#100")
  @Override
  public UserRolesDto getUserInfoByName(String userName) {
    var user =
        usersMbpService.getOne(Wrappers.<Users>lambdaQuery().eq(Users::getUserName, userName));
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
