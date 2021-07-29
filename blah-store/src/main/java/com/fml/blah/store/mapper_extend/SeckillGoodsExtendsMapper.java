package com.fml.blah.store.mapper_extend;

import org.apache.ibatis.annotations.Param;

public interface SeckillGoodsExtendsMapper {

  int reduceStock(@Param("id") Long id, @Param("num") Integer num);
}
