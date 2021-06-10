package com.fml.blah.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fml.blah.remote_interface.user.UserRemoteServiceInterface;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @Autowired private UserRemoteServiceInterface userService;

  @GetMapping("/hello")
  public String helloWorld(@RequestHeader Map<String, String> headers) {
    ObjectMapper objectMapper = new ObjectMapper();
    String header = null;
    try {
      header = objectMapper.writeValueAsString(headers);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    //   var uu = userService.getUserByName("11");
    return header;
  }
}
