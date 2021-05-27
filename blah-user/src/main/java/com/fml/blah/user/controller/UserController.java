package com.fml.blah.user.controller;

import com.fml.blah.common.vo.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @GetMapping("/hello")
  public WebResponse<String> helloWorld() {
    return WebResponse.ok("hello world");
  }
}
