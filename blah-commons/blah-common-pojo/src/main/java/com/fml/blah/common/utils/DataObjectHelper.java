package com.fml.blah.common.utils;

public class DataObjectHelper {
  public static <T> int countAllFields(Class<T> clazz) {

    int count = 0;
    // 找到所有字段
    Class tempClass = clazz;
    while (tempClass != null) {
      count += tempClass.getDeclaredFields().length;
      tempClass = tempClass.getSuperclass();
    }

    return count;
  }
}
