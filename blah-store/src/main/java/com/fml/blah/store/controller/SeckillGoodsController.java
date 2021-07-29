package com.fml.blah.store.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.store.entity.SeckillGoods;
import com.fml.blah.store.service.ISeckillGoodsMbpService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/seckill")
public class SeckillGoodsController {

  @Autowired private ISeckillGoodsMbpService seckillGoodsMbpService;

  @GetMapping("listValid")
  public WebResponse<List<SeckillGoods>> listValid(@RequestParam LocalDateTime time) {

    var list =
        seckillGoodsMbpService.list(
            Wrappers.<SeckillGoods>lambdaQuery()
                .ge(SeckillGoods::getStartTime, time)
                .lt(SeckillGoods::getEndTime, time));

    return WebResponse.ok(list);
  }

  @GetMapping("list")
  public WebResponse<List<SeckillGoods>> list(
      @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
    log.info("list " + startTime + " " + endTime);
    var query = Wrappers.<SeckillGoods>lambdaQuery();
    if (startTime != null) {
      query.ge(SeckillGoods::getStartTime, startTime);
    }

    if (endTime != null) {
      query.lt(SeckillGoods::getEndTime, endTime);
    }

    var list = seckillGoodsMbpService.list(query);
    return WebResponse.ok(list);
  }
}
