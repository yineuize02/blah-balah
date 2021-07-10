package com.fml.blah.seckill.rabbit;

import com.fml.blah.seckill.rabbit.message.SeckillMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeckillSender {

  @Autowired private AmqpTemplate rabbitTemplate;

  public void send(SeckillMessage seckillMessage) {
    this.rabbitTemplate.convertAndSend(RoutingKeys.SECKILL, seckillMessage);
  }
}
