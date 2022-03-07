package com.fml.blah.remote_interface.user;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.user.dto.UserRolesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blah-user", fallback = UserRemoteServiceFallBack.class)
public interface UserRemoteServiceInterface {

  @GetMapping("/users/by_user_name")
  WebResponse<UserRolesDto> getUserByName(@RequestParam String userName);

  @GetMapping("/users/check_password")
  WebResponse<Boolean> checkPassword(@RequestParam String userName, @RequestParam String password);

  @PostMapping("/users/create_user")
  WebResponse<UserRolesDto> createUser(@RequestBody UserAddParam param);

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  class UserAddParam {
    private String userName;
    private String password;
  }
}
