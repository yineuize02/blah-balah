package com.fml.blah.seckill.rabbit;

public interface QueueConstants {
  String SECKILL_ROUTE_KEY = "seckill";
  String SECKILL_QUEUE = "seckill";

  String SECKILL_DIRECT_EXCHANGE = "seckillDirectExchange";

  String DEAD_LETTER_EXCHANGE = "deadLetterExchange";
  String DEAD_LETTER_QUEUE_SECKILL = "deadLetterQueueSeckill";
  String DEAD_LETTER_SECKILL_ROUTING_KEY = "deadLetterSeckillRoutingKey";
}
