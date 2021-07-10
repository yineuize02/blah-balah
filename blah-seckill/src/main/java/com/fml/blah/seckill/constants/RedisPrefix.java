package com.fml.blah.seckill.constants;

public interface RedisPrefix {

  String SECKILL_FULL_STOCK = "skl:fst:";
  String SECKILL_STOCK = "skl:st:";
  String SECKILL_SALED = "skl:sd";
  String SECKILL_GOODS_USER_COUNT = "skl:gd:u:c:";
  String SECKILL_USER_LOCK = "skl:u:lk:";
  String SECKILL_GOODS_LOCK = "skl:gd:lk:";
}
