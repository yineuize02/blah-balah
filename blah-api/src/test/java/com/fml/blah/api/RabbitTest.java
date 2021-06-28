package com.fml.blah.api;

import com.fml.blah.api.rabbit.sender.HelloSender;
import com.fml.blah.test.ServiceTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RabbitTest extends ServiceTestBase {

  @Autowired private HelloSender helloSender;

  @Test
  public void hello() throws InterruptedException {
    helloSender.send();
    Thread.sleep(2000);
  }
}
