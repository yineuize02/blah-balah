package com.fml.blah.seckill.controller;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.seckill.service.PreheatService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreheatController {

  @Autowired private PreheatService preheatService;

  @PostMapping("preheatStock")
  public WebResponse<String> preheatStock(
      @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {

    var result = preheatService.preheatStock(start, end);
    return WebResponse.ok(result);
  }
}
