package com.fml.blah.auth.controller;

import static com.fml.blah.auth.constants.RedisConstants.AUTH_TOKEN;

import cn.hutool.core.util.IdUtil;
import com.fml.blah.auth.constants.RedisConstants;
import com.fml.blah.auth.dto.LoginPayload;
import com.fml.blah.common.dto.UserDetailDto;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.user.UserRemoteServiceInterface;
import com.fml.blah.remote_interface.user.dto.RoleDto;
import com.fml.blah.remote_interface.user.dto.UserRolesDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

  @Autowired private UserRemoteServiceInterface userRemoteService;
  @Autowired private RedisUtils redisUtils;

  @PostMapping("/login")
  public WebResponse<String> login(@Validated @RequestBody LoginPayload payload) {

    WebResponse<Boolean> checkPassword =
        userRemoteService.checkPassword(payload.getUsername(), payload.getPassword());
    if (!checkPassword.getData()) {
      return WebResponse.error("用户名或密码错误");
    }

    String token = IdUtil.simpleUUID() + IdUtil.simpleUUID();
    redisUtils.set(AUTH_TOKEN + token, payload.getUsername(), RedisConstants.AUTH_TOKEN_EXPIRY);
    return WebResponse.ok(token);
  }

  @DeleteMapping("/logout")
  public WebResponse<Boolean> logout(@RequestParam String token) {
    redisUtils.del(AUTH_TOKEN + token);
    return WebResponse.ok(true);
  }

  @GetMapping("/authentication")
  public WebResponse<UserDetailDto> authentication(@RequestParam String token) {
    String username = (String) redisUtils.get(AUTH_TOKEN + token);
    if (username == null) {
      return WebResponse.error(null);
    }

    WebResponse<UserRolesDto> userRes = userRemoteService.getUserByName(username);
    var user = userRes.getData();
    if (user == null) {
      return WebResponse.error(null);
    }

    var userRoles =
        Optional.ofNullable(user.getRoles())
            .map(l -> l.stream().map(RoleDto::getName).collect(Collectors.toList()))
            .orElse(List.of());
    // todo user authorities

    var userDetail = new UserDetailDto();
    userDetail.setUserName(user.getUserName());
    userDetail.setRoles(userRoles);
    userDetail.setId(user.getId());
    return WebResponse.ok(userDetail);
  }
}
