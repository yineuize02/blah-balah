package com.fml.blah.seckill.controller;

import com.fml.blah.common.auth.UserContext;
import com.fml.blah.common.configs.ratelimiter.RateLimit;
import com.fml.blah.common.dto.UserDetailDto;
import com.fml.blah.common.vo.WebResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @RateLimit(limitNum = 100)
  @GetMapping("/hello")
  public WebResponse<UserDetailDto> helloWorld(@RequestHeader Map<String, String> headers) {
    var currentUser = UserContext.currentUser();
    return WebResponse.ok(currentUser);
  }
}
