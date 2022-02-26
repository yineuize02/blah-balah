package com.fml.blah.auth.controller;

import static com.fml.blah.auth.constants.RedisConstants.AUTH_TOKEN;

import cn.hutool.core.util.IdUtil;
import com.fml.blah.auth.constants.RedisConstants;
import com.fml.blah.auth.dto.LoginPayload;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.user.UserRemoteServiceInterface;
import com.fml.blah.remote_interface.user.dto.UserRolesDto;
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
      return new WebResponse<>("登录失败", null);
    }

    String token = IdUtil.simpleUUID() + IdUtil.simpleUUID();
    redisUtils.set(AUTH_TOKEN + token, payload.getUsername(), RedisConstants.AUTH_TOKEN_EXPIRY);
    return new WebResponse<>("login success", token);
  }

  @DeleteMapping("/logout")
  public WebResponse<Boolean> logout(@RequestParam String token) {
    redisUtils.del(AUTH_TOKEN + token);
    return new WebResponse<>("logout success", true);
  }

  @GetMapping("/authentication")
  public WebResponse<UserRolesDto> authentication(@RequestParam String token) {
    String username = (String) redisUtils.get(AUTH_TOKEN + token);
    if (username == null) {
      return new WebResponse<>("auth fail", null);
    }

    WebResponse<UserRolesDto> user = userRemoteService.getUserByName(username);
    return new WebResponse<>("auth success", user.getData());
  }
}
