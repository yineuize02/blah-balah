package com.fml.blah.seckill.service;

import com.fml.blah.common.vo.WebResponse;
import com.fml.blah.remote_interface.store.StoreRemoteServiceInterface;
import com.fml.blah.seckill.constants.RedisPrefix;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PreheatService {

  @Autowired private StoreRemoteServiceInterface storeRemoteService;
  @Autowired private RedissonClient redissonClient;

  /**
   * 库存预热
   *
   * @param startTime
   * @param endTime
   */
  public String preheatStock(LocalDateTime startTime, LocalDateTime endTime) {
    var list =
        Optional.ofNullable(storeRemoteService.list(startTime, endTime))
            .map(WebResponse::getData)
            .orElseThrow();
    var batch = redissonClient.createBatch();
    for (var goods : list) {
      var buk = batch.getBucket(RedisPrefix.SECKILL_STOCK + goods.getId());
      buk.setAsync(goods.getStock());
    }

    var result = batch.execute();
    return result.toString();
  }
}
