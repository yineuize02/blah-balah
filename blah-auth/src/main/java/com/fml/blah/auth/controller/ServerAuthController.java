package com.fml.blah.auth.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fml.blah.auth.dto.ServerAuthPayload;
import com.fml.blah.auth.entity.ServerAuth;
import com.fml.blah.auth.service.IServerAuthMbpService;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.common.vo.WebResponse;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author yrz */
@Slf4j
@RestController
@RequestMapping("/auth/server")
public class ServerAuthController {

  @Autowired private IServerAuthMbpService serverAuthMbpService;
  @Autowired private KeyPair keyPair;
  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private RedisUtils redisUtils;

  @PostMapping("/authentication")
  public WebResponse<String> authentication(@RequestBody ServerAuthPayload payload) {

    ServerAuth serverAuth =
        serverAuthMbpService.getOne(
            Wrappers.lambdaQuery(ServerAuth.class)
                .eq(ServerAuth::getServerId, payload.getServerId()));

    if (serverAuth == null) {
      log.warn("serverId {} not found", payload.getServerId());
      return WebResponse.error("服务器不存在");
    }

    if (!passwordEncoder.matches(payload.getServerPassword(), serverAuth.getServerPassword())) {
      log.warn("serverId {} password not match", payload.getServerId());
      return WebResponse.error("");
    }

    return WebResponse.ok("");
  }

  @GetMapping("/rsa/getPublicKey")
  public WebResponse<RSAPublicKey> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    return WebResponse.ok(publicKey);
  }
}
