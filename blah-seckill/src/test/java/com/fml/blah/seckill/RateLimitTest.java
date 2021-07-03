package com.fml.blah.seckill;

import com.fml.blah.test.ControllerTestBase;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class RateLimitTest extends ControllerTestBase {

  @Test
  public void testHello() {
    //   CountDownLatch countDownLatch = new CountDownLatch(1);

    ExecutorService executor = Executors.newFixedThreadPool(10);
    var start = LocalDateTime.now();
    for (int i = 0; i < 1; ++i) {
      executor.submit(
          () -> {
            var resp = requestWithJSON().when().get("hello").then().extract();
            // countDownLatch.countDown();
            log.info(resp.asString());
          });
    }

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //    try {
    //      countDownLatch.await();
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }

    var dur = Duration.between(start, LocalDateTime.now());
    Assert.assertTrue(dur.getSeconds() > 5);
  }
}
