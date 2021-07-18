package com.fml.blah.store.rabbit;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfig {

  @Bean
  CustomExchange orderDelayCancelExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(
        QueueConstants.ORDER_DELAY_CANCEL_EXCHANGE, "x-delayed-message", true, false, args);
  }

  @Bean
  public Queue orderDelayCancelQueue() {
    return new Queue(QueueConstants.ORDER_DELAY_CANCEL_QUEUE);
  }

  @Bean
  public Binding orderDelayCancelBinding() {
    return BindingBuilder.bind(orderDelayCancelQueue())
        .to(orderDelayCancelExchange())
        .with(QueueConstants.ORDER_DELAY_CANCEL)
        .noargs();
  }

  @Bean
  public Queue seckillQueue() {
    return new Queue(QueueConstants.SECKILL_QUEUE);
  }
}
