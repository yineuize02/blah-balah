package com.fml.blah.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTestBase {

  @LocalServerPort protected int port;
  @Getter private RequestSpecification requestSpecification;

  /** before test */
  public void setup() {
    this.requestSpecification = new RequestSpecBuilder().setPort(port).build();
  }

  /**
   * 开始一个 request spec
   *
   * @return
   */
  protected RequestSpecification request() {
    return RestAssured.given(this.getRequestSpecification()).log().ifValidationFails();
  }

  /**
   * 开始一个 JSON request spec
   *
   * @return
   */
  protected RequestSpecification requestWithJSON() {
    return request().contentType(ContentType.JSON);
  }

  protected String getEndpoint(String... parts) {
    var sb = new StringBuilder();
    for (var p : parts) {
      sb.append("/");
      sb.append(p);
    }
    return sb.toString();
  }
}
