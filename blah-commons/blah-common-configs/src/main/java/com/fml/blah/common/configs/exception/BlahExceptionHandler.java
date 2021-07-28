package com.fml.blah.common.configs.exception;

import com.fml.blah.common.vo.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class BlahExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = Exception.class)
  public WebResponse handle(Exception e) {
    log.error("unhandled error", e);
    return WebResponse.error(e.getMessage());
  }
}
