package com.fml.blah.user.controller;

import com.fml.blah.common.vo.WebResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_roles")
public class UserRolesController {

  @PostMapping
  public WebResponse<String> create(@RequestParam String userId) {

    return WebResponse.ok(userId);
  }
}
