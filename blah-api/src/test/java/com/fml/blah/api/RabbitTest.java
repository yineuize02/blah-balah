package com.fml.blah.api;

import com.fml.blah.api.rabbit.sender.HelloSender;
import com.fml.blah.test.ServiceTestBase;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RabbitTest extends ServiceTestBase {

  @Autowired private HelloSender helloSender;
  @Autowired private RabbitTemplate rabbitTemplate;

  @Test
  public void hello() throws InterruptedException {
    helloSender.send();
    Thread.sleep(2000);
  }

  @Test
  public void testDirect() throws InterruptedException {
    rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", Map.of("key", "vvv"));
    Thread.sleep(2000);
  }

  @Test
  public void testFanoutMessage() throws InterruptedException {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "message: testFanoutMessage ";
    String createTime =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("createTime", createTime);
    rabbitTemplate.convertAndSend("fanoutExchange", null, map);
    Thread.sleep(2000);
  }
}
