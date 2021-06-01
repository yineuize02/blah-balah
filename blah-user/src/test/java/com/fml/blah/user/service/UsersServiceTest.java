package com.fml.blah.user.service;

import com.fml.blah.test.ServiceTestBase;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.mapper.UsersMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UsersServiceTest extends ServiceTestBase {
  @Autowired private UsersMapper usersMapper;
  @Autowired private IUsersService usersService;

  @Test
  public void testBatchUpdate() {
    var time = System.currentTimeMillis();
    Users user1 = new Users();
    user1.setId(1L);
    user1.setPassword("foo_" + time);
    user1.setUserName("bar_" + time);

    Users user2 = new Users();
    user2.setId(2L);
    user2.setPassword("foo2_" + time);
    user2.setUserName("ba2_" + time);

    Users user3 = new Users();
    user3.setId(9999L);
    user3.setPassword("foo3_" + time);
    user3.setUserName("bar3_" + time);
    user3.setCreatedAt(LocalDateTime.now());
    user3.setUpdatedAt(LocalDateTime.now());

    var users = List.of(user1, user2, user3);
    usersMapper.batchInsertOrUpdate(users);

    var u3 = usersMapper.selectById(user3.getId());
    Assert.assertEquals(user3.getUserName(), u3.getUserName());
  }
}
