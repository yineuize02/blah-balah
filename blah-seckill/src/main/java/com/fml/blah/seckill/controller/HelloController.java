package com.fml.blah.seckill.controller;

import com.fml.blah.common.configs.ratelimiter.RateLimit;
import com.fml.blah.common.vo.WebResponse;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @RateLimit(limitNum = 4)
  @GetMapping("/hello")
  public WebResponse<String> helloWorld(@RequestHeader Map<String, String> headers) {
    return WebResponse.ok("hello" + LocalDateTime.now());
  }
}
