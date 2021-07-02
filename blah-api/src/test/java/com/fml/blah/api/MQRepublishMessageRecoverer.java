package com.fml.blah.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MQRepublishMessageRecoverer implements MessageRecoverer {

  @Autowired private RabbitTemplate rabbitTemplate;
  @Autowired private MessageConverter msgConverter;

  @Override
  public void recover(Message message, Throwable cause) {
    Map headers = message.getMessageProperties().getHeaders();
    headers.put("x-exception-stacktrace", getStackTraceAsString(cause));
    headers.put(
        "x-exception-message",
        cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage());
    headers.put("x-original-exchange", message.getMessageProperties().getReceivedExchange());
    headers.put("x-original-routingKey", message.getMessageProperties().getReceivedRoutingKey());
    this.rabbitTemplate.send(
        message.getMessageProperties().getReceivedExchange(),
        message.getMessageProperties().getReceivedRoutingKey(),
        message);
    log.error(
        "handler msg (" + msgConverter.fromMessage(message) + ") err, republish to mq.", cause);
  }

  private String getStackTraceAsString(Throwable cause) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter, true);
    cause.printStackTrace(printWriter);
    return stringWriter.getBuffer().toString();
  }
}
