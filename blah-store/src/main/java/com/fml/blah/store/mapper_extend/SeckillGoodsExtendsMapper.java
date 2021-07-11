package com.fml.blah.store.mapper_extend;

import com.fml.blah.store.entity.SeckillGoods;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeckillGoodsExtendsMapper {

  List<SeckillGoods> selectForUpdate(@Param("id") Long id);

  Integer reduceStock(@Param("id") Long id);
}
