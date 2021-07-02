package com.fml.blah.api.rabbit.receiver;

import com.fml.blah.api.rabbit.sender.HelloSender.AA;
import java.time.LocalDateTime;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloReceiver {

  @RabbitListener(queues = "hello")
  @RabbitHandler
  public void process(AA hello) {
    System.out.println("Receiver  : " + hello + " " + LocalDateTime.now().toString());
  }
}
