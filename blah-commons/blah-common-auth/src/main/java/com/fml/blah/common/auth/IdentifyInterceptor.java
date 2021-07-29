package com.fml.blah.common.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fml.blah.common.dto.UserDetailDto;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class IdentifyInterceptor implements HandlerInterceptor {

  private static ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    UserContext.clear();

    var authConfig = AuthConfig.getInstance();

    var jwt = request.getHeader("authorization");
    if (jwt == null) {
      return true;
    }

    String realToken = jwt.replace("Bearer ", "");

    var valid = isSignatureValid(realToken, authConfig.getJwtPublicKey());
    if (!valid) {
      throw new RuntimeException("token invalid");
    }

    JWSObject jwsObject = JWSObject.parse(realToken);
    String userStr = jwsObject.getPayload().toString();

    var userDetail = objectMapper.readValue(userStr, UserDetailDto.class);
    UserContext.setUser(userDetail);
    return true;
  }

  private boolean isSignatureValid(String token, RSAPublicKey publicKey) {
    // Parse the JWS and verify its RSA signature
    SignedJWT signedJWT;
    try {
      signedJWT = SignedJWT.parse(token);
      JWSVerifier verifier = new RSASSAVerifier(publicKey);
      return signedJWT.verify(verifier);
    } catch (ParseException | JOSEException e) {
      return false;
    }
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
      throws Exception {
    UserContext.clear();
  }
}
