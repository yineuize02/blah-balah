package com.fml.blah.seckill.controller;

import com.fml.blah.common.auth.UserContext;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeckillController {

  @Autowired private SeckillService seckillService;

  @PostMapping("seckill/goods/{id}")
  public WebResponse<Boolean> seckill(@PathVariable Long id) {
    var userId = UserContext.currentUser().getId();
    var result = seckillService.doSeckill(id, userId);
    return WebResponse.ok(result);
  }
}
