package com.fml.blah.seckill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.seckill.service.PreheatService;
import java.io.IOException;
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
  public static class PreheatStockParam {
    private LocalDateTime start;
    private LocalDateTime end;
  }

  public static void main(String[] a) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    PreheatStockParam p = new PreheatStockParam();
    p.setStart(LocalDateTime.now());
    p.setEnd(LocalDateTime.of(2050, 1, 1, 1, 1));
    try {
      var s = mapper.writeValueAsString(p);
      System.out.println(s);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    String mm = "{\"start\":[2021,7,28,20,13,49,254007800],\"end\":[2050,1,1,1,1]}";

    String mm2 = "{\"start\":\"2021-07-28T19:42:10.315166200\",\"end\":[2050,1,1,1,1]}";
    try {
      var dd = mapper.createParser(mm).readValueAs(PreheatStockParam.class);
      System.out.println(dd);
      var dd2 = mapper.createParser(mm2).readValueAs(PreheatStockParam.class);
      System.out.println(dd2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @PostMapping("preheatStock")
  public WebResponse<String> preheatStock(@RequestBody PreheatStockParam param) {

    var result = preheatService.preheatStock(param.getStart(), param.getEnd());
    return WebResponse.ok(result);
  }
}
