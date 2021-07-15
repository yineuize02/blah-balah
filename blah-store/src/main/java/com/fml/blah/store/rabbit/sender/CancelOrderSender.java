package com.fml.blah.store.rabbit.sender;

import com.fml.blah.store.rabbit.QueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderSender {

  @Autowired private AmqpTemplate amqpTemplate;

  public void sendMessage(Long orderId, final long delayTimes) {
    // 给延迟队列发送消息
    amqpTemplate.convertAndSend(
        QueueConstants.ORDER_DELAY_CANCEL_EXCHANGE,
        QueueConstants.ORDER_DELAY_CANCEL,
        orderId,
        new MessagePostProcessor() {
          @Override
          public Message postProcessMessage(Message message) throws AmqpException {
            // 给消息设置延迟毫秒值
            message.getMessageProperties().setHeader("x-delay", delayTimes);
            return message;
          }
        });
    log.info("send delay message orderId:{}", orderId);
  }

  @Async
  public void sendCancelOrderDelay(Long orderId, Long delay) {
    sendMessage(orderId, delay);
  }
}
