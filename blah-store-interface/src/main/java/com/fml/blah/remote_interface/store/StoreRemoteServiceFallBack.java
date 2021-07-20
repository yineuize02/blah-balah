package com.fml.blah.remote_interface.store;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.store.dto.SeckillGoodsDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreRemoteServiceFallBack implements StoreRemoteServiceInterface {

  @Override
  public WebResponse<List<SeckillGoodsDto>> list(LocalDateTime startTime, LocalDateTime endTime) {
    log.error("list fallback startTime endTime: " + startTime + " , " + endTime);
    return WebResponse.fallback(null);
  }
}
