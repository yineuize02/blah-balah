package com.fml.blah.store.rabbit;

import static com.fml.blah.store.rabbit.QueueConstants.DEAD_LETTER_EXCHANGE;
import static com.fml.blah.store.rabbit.QueueConstants.DEAD_LETTER_ORDER_DELAY_CANCEL_ROUTING_KEY;
import static com.fml.blah.store.rabbit.QueueConstants.DEAD_LETTER_QUEUE_ORDER_DELAY_CANCEL;
import static com.fml.blah.store.rabbit.QueueConstants.ORDER_DELAY_CANCEL_QUEUE;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
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
    Map<String, Object> args = new HashMap<>(2);
    // 如果队列配置了参数 x-dead-letter-routing-key 的话，“死信”的路由key将会被替换成该参数对应的值。
    // 如果没有设置，则保留该消息原有的路由key。
    //
    // 举个栗子：
    //
    // 如果原有消息的路由key是testA，被发送到业务Exchage中，然后被投递到业务队列QueueA中，
    // 如果该队列没有配置参数x-dead-letter-routing-key，则该消息成为死信后，将保留原有的路由keytestA，
    // 如果配置了该参数，并且值设置为testB，那么该消息成为死信后，路由key将会被替换为testB，然后被抛到死信交换机中。
    //
    // 另外，由于被抛到了死信交换机，所以消息的Exchange Name也会被替换为死信交换机的名称。

    // 常见问题：
    // 1、consumer启动时报告异常 com.rabbitmq.client.ShutdownSignalException: channel error; protocol
    // method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent
    // arg 'x-dead-letter-exchange' for queue 'queue_emailsend_result' in vhost 'host_test':
    // received the value 'dlx' of type 'longstr' but current is none, class-id=50, method-id=10)
    //     原因：queue已经存在，但是启动 consumer 时试图设定一个 x-dead-letter-exchange 参数，这和服务器上的定义不一样，server
    // 不允许所以报错。如果删除 queue 重新 declare 则不会有问题。或者通过 policy 来设置这个参数也可以不用删除队列。

    //       x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
    args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
    //       x-dead-letter-routing-key  这里声明当前队列的死信路由key
    args.put("x-dead-letter-routing-key", DEAD_LETTER_ORDER_DELAY_CANCEL_ROUTING_KEY);
    return QueueBuilder.durable(ORDER_DELAY_CANCEL_QUEUE).withArguments(args).build();
  }

  // 声明死信Exchange
  @Bean
  public DirectExchange deadLetterExchange() {
    return new DirectExchange(DEAD_LETTER_EXCHANGE);
  }

  // 声明死信队列Seckill
  @Bean
  public Queue deadLetterQueueOrderDelayCancel() {
    return new Queue(DEAD_LETTER_QUEUE_ORDER_DELAY_CANCEL);
  }

  @Bean
  public Binding deadLetterBindingSeckill(
      @Qualifier("deadLetterQueueOrderDelayCancel") Queue queue,
      @Qualifier("deadLetterExchange") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ORDER_DELAY_CANCEL_ROUTING_KEY);
  }

  @Bean
  public Binding orderDelayCancelBinding() {
    return BindingBuilder.bind(orderDelayCancelQueue())
        .to(orderDelayCancelExchange())
        .with(QueueConstants.ORDER_DELAY_CANCEL)
        .noargs();
  }
}
