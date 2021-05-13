package com.fml.blah.oauth.exception;

import com.fml.blah.common.vo.WebResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class Oauth2ExceptionHandler {
  @ResponseBody
  @ExceptionHandler(value = OAuth2Exception.class)
  public WebResponse handleOauth2(OAuth2Exception e) {
    return WebResponse.unauthorized(e.getMessage());
  }
}
