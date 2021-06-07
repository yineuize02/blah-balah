package com.fml.blah.user.controller;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.user.entity.UsersRoles;
import com.fml.blah.user.service.RolesService;
import com.fml.blah.user.service.UsersService.UserRoleCreateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_roles")
public class UserRolesController {

  @Autowired private RolesService rolesService;

  @PostMapping
  public WebResponse<UsersRoles> create(@RequestBody UserRoleCreateParam param) {

    var result = rolesService.createUserRoles(param);

    return WebResponse.ok(result);
  }
}
