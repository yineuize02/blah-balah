package com.fml.blah.user.remote_interface;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.user.remote_interface.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blah-user", fallback = UserRemoteServiceFallBack.class)
public interface UserRemoteServiceInterface {

  @GetMapping("/getByUserName")
  WebResponse<UserDto> getUserByName(@RequestParam String userName);
}
