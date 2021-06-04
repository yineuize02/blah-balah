package com.fml.blah.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.entity_table_field_name.UsersFieldNames;
import com.fml.blah.user.remote_interface.dto.UserDto;
import com.fml.blah.user.service.IUsersMbpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 前端控制器
 *
 * @author y
 * @since 2021-05-28
 */
@Controller
public class UsersController {

  @Autowired private IUsersMbpService usersMbpService;

  @GetMapping("/hello")
  public WebResponse<String> helloWorld() {
    return WebResponse.ok("hello world");
  }

  @GetMapping("/getByUserName")
  public WebResponse<UserDto> getUserByName(@RequestParam String userName) {
    var user =
        usersMbpService.getOne(new QueryWrapper<Users>().eq(UsersFieldNames.userName, userName));
    var userDto = new UserDto();
    BeanUtils.copyProperties(user, userDto);
    return WebResponse.ok(userDto);
  }
}
