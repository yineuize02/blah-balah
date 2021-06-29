package com.fml.blah.api;

import com.fml.blah.common.rabbit.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

@AutoConfigureAfter(RabbitConfig.class)
@Component
public class MyApplicationRunner implements ApplicationRunner {

  @Autowired private RabbitTemplate rabbitTemplate;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    rabbitTemplate.setConfirmCallback(
        (correlationData, ack, cause) -> {
          System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
          System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
          System.out.println("ConfirmCallback:     " + "原因：" + cause);
        });

    rabbitTemplate.setReturnCallback(
        (message, replyCode, replyText, exchange, routingKey) -> {
          System.out.println("ReturnCallback:     " + "消息：" + message);
          System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
          System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
          System.out.println("ReturnCallback:     " + "交换机：" + exchange);
          System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
        });
  }
}
