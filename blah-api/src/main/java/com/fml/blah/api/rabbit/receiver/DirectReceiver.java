package com.fml.blah.api.rabbit.receiver;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiver {
  @RabbitListener(queues = "TestDirectQueue") // 监听的队列名称 TestDirectQueue
  @RabbitHandler
  public void process(Map testMessage, Channel channel, Message message) throws IOException {
    System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    //  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }
}
