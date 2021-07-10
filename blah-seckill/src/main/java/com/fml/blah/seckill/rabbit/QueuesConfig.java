package com.fml.blah.seckill.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfig {

  @Bean
  public Queue SeckillQueue() {
    return new Queue("seckill");
  }

  @Bean
  DirectExchange SeckillDirectExchange() {
    return new DirectExchange("SeckillDirectExchange", true, false);
  }

  @Bean
  Binding bindingDirect() {
    return BindingBuilder.bind(SeckillQueue())
        .to(SeckillDirectExchange())
        .with(RoutingKeys.SECKILL);
  }
}
