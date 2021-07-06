package com.fml.blah.common.configs.exception;

import com.fml.blah.common.vo.WebResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BlahExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = Exception.class)
  public WebResponse handle(Exception e) {
    return WebResponse.error(e.getMessage());
  }
}
