package com.fml.blah.api.rabbit.sender;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender {

  @Autowired private AmqpTemplate rabbitTemplate;

  @Data
  public static class AA {
    private String msg;
  }

  public void send() {
    String str = "hello " + LocalDateTime.now().toString();
    System.out.println("Sender : " + str);

    var aa = new AA();
    aa.setMsg(str);
    this.rabbitTemplate.convertAndSend("hello", aa);
  }
}
