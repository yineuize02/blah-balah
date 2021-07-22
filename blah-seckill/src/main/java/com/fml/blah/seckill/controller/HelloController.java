package com.fml.blah.seckill.controller;

import cn.hutool.json.JSONUtil;
import com.fml.blah.common.configs.ratelimiter.RateLimit;
import com.fml.blah.common.vo.WebResponse;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @RateLimit(limitNum = 100)
  @GetMapping("/hello")
  public WebResponse<String> helloWorld(@RequestHeader Map<String, String> headers) {
    //    var jwt =
    return WebResponse.ok("hello" + LocalDateTime.now() + JSONUtil.toJsonStr(headers));
  }
}
