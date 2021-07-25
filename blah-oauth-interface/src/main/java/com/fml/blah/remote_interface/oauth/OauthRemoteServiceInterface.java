package com.fml.blah.remote_interface.oauth;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "blah-oauth", fallback = OauthRemoteServiceFallBack.class)
public interface OauthRemoteServiceInterface {
  @GetMapping("/rsa/getPublicKey")
  Map<String, Object> getKey();
}
