package com.fml.blah.seckill.controller;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.seckill.service.PreheatService;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreheatController {

  @Autowired private PreheatService preheatService;

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  public class PreheatStockParam {
    private LocalDateTime start;
    private LocalDateTime end;
  }

  @PostMapping("preheatStock")
  public WebResponse<String> preheatStock(@RequestBody PreheatStockParam param) {

    var result = preheatService.preheatStock(param.getStart(), param.getEnd());
    return WebResponse.ok(result);
  }
}
