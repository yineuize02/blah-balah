package com.fml.blah.auth;

import static com.nimbusds.jose.JWSAlgorithm.RS256;

import com.fml.blah.test.ServiceTestBase;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import java.security.KeyPair;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** @author yrz */
public class JwtTest extends ServiceTestBase {
  @Autowired private KeyPair keyPair;

  @SneakyThrows
  @Test
  public void testJwt() {

    JWSHeader jwsHeader =
        new JWSHeader.Builder(RS256) // 加密算法
            .type(JOSEObjectType.JWT) // 静态常量
            .build();

    Payload payload = new Payload("hello world");

    JWSSigner signer = new RSASSASigner(keyPair.getPrivate());
    JWSObject jwsObject = new JWSObject(jwsHeader, payload);
    jwsObject.sign(signer);
    String token = jwsObject.serialize();
    System.out.println(token);

    ///  JWSVerifier jwsVerifier = new RSASSAVerifier(keyPair.getPublic());
  }
}
