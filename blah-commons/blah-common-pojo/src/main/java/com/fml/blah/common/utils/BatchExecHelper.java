package com.fml.blah.common.utils;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class BatchExecHelper {

  // 参数最大限制个数
  public static final int maxLimit = 20000;

  public static <T> int batchExec(
      Collection<T> data, Function<List<T>, Integer> func, Class<T> clazz) {
    var fieldLength = DataObjectHelper.countAllFields(clazz);

    var batchSize = maxLimit / fieldLength;
    return batchExec(data, func, batchSize);
  }

  public static <T> int batchExec(
      Collection<T> data, Function<List<T>, Integer> func, int batchSize) {
    var size = data.size();
    Integer sum = 0;
    for (int offset = 0; offset < size; offset += batchSize) {
      var batch = data.stream().skip(offset).limit(batchSize).collect(toList());
      var result = func.apply(batch);
      if (result != null) {
        sum += result;
      }
    }

    return sum;
  }

  // 批量查询
  public static <T, K> Collection<T> batchQuery(
      Collection<K> keys, Function<Collection<K>, Collection<T>> func) {
    return batchQuery(keys, func, maxLimit);
  }

  public static <T, K> Collection<T> batchQuery(
      Collection<K> keys, Function<Collection<K>, Collection<T>> func, int limitLength) {
    var size = keys.size();
    return Stream.iterate(0, offset -> offset < size, offset -> offset + limitLength)
        .map(offset -> keys.stream().skip(offset).limit(limitLength).collect(toList()))
        .flatMap(k -> func.apply(k).stream())
        .collect(toList());
  }
}
