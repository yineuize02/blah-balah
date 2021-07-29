package com.fml.blah.remote_interface.store;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.store.dto.SeckillGoodsDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blah-store", fallback = StoreRemoteServiceFallBack.class)
public interface StoreRemoteServiceInterface {

  @GetMapping("seckill/list")
  WebResponse<List<SeckillGoodsDto>> list(
      @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime);
}
