package com.fml.blah.user.controller;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.user.entity.Roles;
import com.fml.blah.user.service.RolesService;
import com.fml.blah.user.service.RolesService.RolesCreateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {

  @Autowired private RolesService rolesService;

  @PostMapping
  public WebResponse<Roles> createRoles(@RequestBody RolesCreateParam roleParam) {
    var role = rolesService.createRole(roleParam);
    return WebResponse.ok(role);
  }
}
