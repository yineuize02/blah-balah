package com.fml.blah.common.configs.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ConditionalOnClass(HttpServletRequest.class)
@Component
public class FeignTokenInterceptor implements RequestInterceptor {

  @Value("{blah.server-secret}")
  private String serverSecret;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    var request = getHttpServletRequest();
    if (null == getHttpServletRequest()) {
      return;
    }

    // 将获取Token对应的值往下面传
    var headers = getHeaders(request);
    headers.put("server-secret", serverSecret);

    transferHeader(headers, "token", requestTemplate);
    transferHeader(headers, "rpc-token", requestTemplate);
    transferHeader(headers, "server-secret", requestTemplate);
  }

  private void transferHeader(
      Map<String, String> headers, String key, RequestTemplate requestTemplate) {
    var h = headers.get(key);
    if (h != null) {
      requestTemplate.header(key, h);
    }
  }

  private HttpServletRequest getHttpServletRequest() {
    try {

      return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Feign拦截器拦截请求获取Token对应的值
   *
   * @param request
   * @return
   */
  private Map<String, String> getHeaders(HttpServletRequest request) {
    Map<String, String> map = new LinkedHashMap<>();
    Enumeration<String> enumeration = request.getHeaderNames();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }
    return map;
  }
}
