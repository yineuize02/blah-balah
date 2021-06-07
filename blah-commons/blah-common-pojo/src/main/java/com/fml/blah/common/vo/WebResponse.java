package com.fml.blah.common.vo;

import com.fml.blah.common.constants.ResponseMessageConstants;
import java.io.Serializable;
import lombok.Data;

@Data
public class WebResponse<T> implements Serializable {

  private String message;
  private T data;

  public WebResponse(String message, T data) {
    this.message = message;
    this.data = data;
  }

  public static <T> WebResponse<T> ok(T data) {
    return new WebResponse<>(ResponseMessageConstants.OK, data);
  }

  public static <T> WebResponse<T> error(T data) {
    return new WebResponse<>(ResponseMessageConstants.ERROR, data);
  }

  public static <T> WebResponse<T> fallback(T data) {
    return new WebResponse<>(ResponseMessageConstants.FALLBACK, data);
  }

  public static <T> WebResponse<T> unauthorized(T data) {
    return new WebResponse<>(ResponseMessageConstants.UNAUTHORIZED, data);
  }
}
