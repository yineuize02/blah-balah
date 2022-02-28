package com.fml.blah.auth.controller;

import static com.nimbusds.jose.JWSAlgorithm.RS256;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fml.blah.auth.dto.ServerAuthPayload;
import com.fml.blah.auth.entity.ServerAuth;
import com.fml.blah.auth.service.IServerAuthMbpService;
import com.fml.blah.auth.vo.ServerAuthJwt;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.common.vo.WebResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import lombok.SneakyThrows;
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

  @SneakyThrows
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

    var jwt = generateJwt(payload);

    return WebResponse.ok(jwt);
  }

  @GetMapping("/rsa/getPublicKey")
  public WebResponse<RSAPublicKey> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    return WebResponse.ok(publicKey);
  }

  private String generateJwt(ServerAuthPayload payload) throws JOSEException {
    JWSHeader jwsHeader = new JWSHeader.Builder(RS256).type(JOSEObjectType.JWT).build();
    var authJwt = new ServerAuthJwt();
    authJwt.setId(payload.getServerId());
    var jsonJwt = JSONUtil.toJsonStr(authJwt);
    Payload jwtPayload = new Payload(jsonJwt);

    JWSSigner signer = new RSASSASigner(keyPair.getPrivate());
    JWSObject jwsObject = new JWSObject(jwsHeader, jwtPayload);
    jwsObject.sign(signer);
    return jwsObject.serialize();
  }
}
