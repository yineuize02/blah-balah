package com.fml.blah.seckill.ratelimiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiting {

  // 默认每秒放入桶中的token
  int limitNum() default 100;

  String name() default "";

  boolean block() default false;
}
