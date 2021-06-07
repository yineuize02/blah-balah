package com.fml.blah.user.controller;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.user.remote_interface.dto.UserRolesDto;
import com.fml.blah.user.service.UsersService;
import com.fml.blah.user.service.UsersService.UserAddParam;
import com.fml.blah.user.vo.UserVo;
import javax.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author y
 * @since 2021-05-28
 */
@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired private UsersService usersService;

  @GetMapping("/hello")
  public WebResponse<String> helloWorld() {
    return WebResponse.ok("hello world");
  }

  @GetMapping("get_by_user_name")
  public WebResponse<UserRolesDto> getUserByName(@RequestParam @NotNull String userName) {

    var userDto = usersService.getUserInfoByName(userName);
    return WebResponse.ok(userDto);
  }

  @PostMapping("create_user")
  public WebResponse<UserVo> createUser(@RequestBody UserAddParam param) {
    var user = usersService.addUser(param);
    var userVo = new UserVo();
    BeanUtils.copyProperties(user, userVo);
    return WebResponse.ok(userVo);
  }
}
