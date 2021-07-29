package com.fml.blah.store.rabbit.receiver;

import com.fml.blah.store.rabbit.QueueConstants;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderReceiver {

  @RabbitListener(queues = QueueConstants.ORDER_DELAY_CANCEL_QUEUE) // 监听的队列名称 TestDirectQueue
  @RabbitHandler
  public void process(Integer id, Channel channel, Message message) throws IOException {
    log.info("CancelOrderReceiver消费者收到消息  : " + id);
    //  throw new RuntimeException("haha");
  }
}
