package com.fml.blah.seckill.config;

import com.nimbusds.jose.JWSObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class IdentifyInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    var jwt = request.getHeader("authorization");

    String realToken = jwt.replace("Bearer ", "");

    JWSObject jwsObject = JWSObject.parse(realToken);

    String userStr = jwsObject.getPayload().toString();
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable ModelAndView modelAndView)
      throws Exception {}

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable Exception ex)
      throws Exception {}
}
