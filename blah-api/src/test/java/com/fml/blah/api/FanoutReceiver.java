package com.fml.blah.api;

import java.util.Map;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {

  @RabbitListener(queues = "fanout.A")
  @RabbitHandler
  public void processA(Map testMessage) {
    System.out.println("FanoutReceiverA消费者收到消息  : " + testMessage.toString());
  }

  @RabbitListener(queues = "fanout.B")
  @RabbitHandler
  public void processB(Map testMessage) {
    System.out.println("FanoutReceiverB消费者收到消息  : " + testMessage.toString());
  }

  @RabbitListener(queues = "fanout.C")
  @RabbitHandler
  public void processC(Map testMessage) {
    System.out.println("FanoutReceiverC消费者收到消息  : " + testMessage.toString());
  }
}
