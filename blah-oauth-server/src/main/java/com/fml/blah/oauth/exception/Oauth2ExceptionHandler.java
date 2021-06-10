package com.fml.blah.oauth.exception;

import com.fml.blah.common.vo.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class Oauth2ExceptionHandler {
  @ResponseBody
  @ExceptionHandler(value = OAuth2Exception.class)
  public WebResponse handleOauth2(OAuth2Exception e) {
    log.error(e.getSummary());
    return WebResponse.unauthorized(e.getMessage());
  }
}
