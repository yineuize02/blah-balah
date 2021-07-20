package com.fml.blah.store.rabbit.receiver;

import com.fml.blah.store.rabbit.QueueConstants;
import com.fml.blah.store.service.SeckillGoodsService;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeckillReceiver {

  @Autowired private SeckillGoodsService seckillGoodsService;

  @RabbitListener(queues = QueueConstants.SECKILL_QUEUE) // 监听的队列名称 TestDirectQueue
  @RabbitHandler
  public void process(SeckillMessage seckillMessage, Channel channel, Message message)
      throws IOException {
    log.info("DirectReceiver消费者收到消息  : " + seckillMessage.toString());
    seckillGoodsService.onSeckill(seckillMessage);
    //  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }
}
