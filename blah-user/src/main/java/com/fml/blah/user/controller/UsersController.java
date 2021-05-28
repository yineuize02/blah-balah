package com.fml.blah.user.controller;

import com.fml.blah.common.vo.WebResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前端控制器
 *
 * @author y
 * @since 2021-05-28
 */
@Controller
public class UsersController {
  @GetMapping("/hello")
  public WebResponse<String> helloWorld() {
    return WebResponse.ok("hello world");
  }
}
