package com.fml.blah.remote_interface.auth;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.auth.dto.ServerAuthPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/** @author yrz */
@FeignClient(value = "blah-auth", fallback = AuthRemoteServiceFallBack.class)
public interface AuthRemoteServiceInterface {

  @GetMapping("/auth/server/authentication")
  WebResponse<String> serverAuthentication(ServerAuthPayload payload);
}
