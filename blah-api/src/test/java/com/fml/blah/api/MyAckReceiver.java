// package com.fml.blah.api;
//
// import com.rabbitmq.client.Channel;
// import org.springframework.amqp.core.Message;
// import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
// import org.springframework.amqp.support.converter.MessageConverter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
//
// @Component
// public class MyAckReceiver implements ChannelAwareMessageListener {
//  @Autowired private MessageConverter msgConverter;
//
//  @Override
//  public void onMessage(Message message, Channel channel) throws Exception {
//
//    long deliveryTag = message.getMessageProperties().getDeliveryTag();
//    try {
//      // var obj = msgConverter.fromMessage(message);
//
//      // 因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
//      String msg = message.toString();
//      String[] msgArray = msg.split("'"); // 可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
//      System.out.println("  MyAckReceiver " + msg);
//      System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
//      channel.basicAck(
//          deliveryTag, true); // 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//      //			channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
//    } catch (Exception e) {
//      channel.basicReject(deliveryTag, false);
//      e.printStackTrace();
//    }
//  }
// }
