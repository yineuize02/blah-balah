package com.fml.blah.seckill.controller;

import com.fml.blah.common.vo.WebResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeckillController {

  @PostMapping("seckill")
  public WebResponse<String> seckill() {

    return null;
  }
}
