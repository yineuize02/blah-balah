package com.fml.blah.store.rabbit;

public interface QueueConstants {
  String ORDER_DELAY_CANCEL_EXCHANGE = "order_delay_cancel_exchange";
  String ORDER_DELAY_CANCEL_QUEUE = "order_delay_cancel_queue";
  String ORDER_DELAY_CANCEL = "order_delay_cancel";
  String SECKILL_QUEUE = "seckill";
  String DEAD_LETTER_EXCHANGE = "deadLetterExchange";
  String DEAD_LETTER_ORDER_DELAY_CANCEL_ROUTING_KEY = "dead_letter_order_delay_cancel_routing_key";
  String DEAD_LETTER_QUEUE_ORDER_DELAY_CANCEL = "dead_letter_queue_order_delay_cancel";
  Long ORDER_DELAY_CANCEL_TIME = 1000 * 10L;
}
