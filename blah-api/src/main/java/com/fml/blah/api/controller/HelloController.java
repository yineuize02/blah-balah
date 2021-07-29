package com.fml.blah.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello/{id}")
  public String helloWorld(@PathVariable String id, @RequestHeader Map<String, String> headers) {
    ObjectMapper objectMapper = new ObjectMapper();
    String header = null;
    try {
      header = objectMapper.writeValueAsString(headers);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return header + " id: " + id;
  }
}
