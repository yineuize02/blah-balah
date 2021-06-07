package com.fml.blah.user.controller;

import com.fml.blah.test.ControllerTestBase;
import com.fml.blah.user.service.UsersService.UserAddParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class UsersControllerTest extends ControllerTestBase {
  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  @Before
  public void setup() {
    super.setup();
  }

  @Test
  public void testCreate() {
    jdbcTemplate.execute("truncate table blah_user.users;");

    var createParam = UserAddParam.builder().userName("11").password("22").build();
    var resp =
        this.requestWithJSON()
            .body(createParam)
            .when()
            .post(this.getEndpoint("users", "create_user"))
            .then()
            .extract();

    log.info(resp.asString());
    Assert.assertEquals(200, resp.statusCode());
  }
}
