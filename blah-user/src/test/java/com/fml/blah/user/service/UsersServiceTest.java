package com.fml.blah.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fml.blah.common.utils.BatchExecHelper;
import com.fml.blah.test.ServiceTestBase;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.entity_table_field_name.UsersFieldNames;
import com.fml.blah.user.mapper.UsersMapper;
import com.fml.blah.user.mapper_extend.UsersExtendMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class UsersServiceTest extends ServiceTestBase {
  @Autowired private UsersMapper usersMapper;
  @Autowired private UsersExtendMapper usersExtendMapper;
  @Autowired private JdbcTemplate jdbcTemplate;

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
    usersExtendMapper.batchInsertOrUpdate(users);

    var u3 = usersMapper.selectById(user3.getId());
    Assert.assertEquals(user3.getUserName(), u3.getUserName());
  }

  @Test
  public void testMax() {

    jdbcTemplate.execute("truncate table blah_user.users;");
    int max = 50011;
    List<Users> usersList = new ArrayList<>(max);

    for (int i = 0; i < max; i++) {
      var time = System.currentTimeMillis();
      var user = new Users();
      user.setId(Integer.toUnsignedLong(i + 1));
      user.setUserName("foo" + time);
      user.setPassword("bar" + time);
      user.setCreatedAt(LocalDateTime.now());
      user.setUpdatedAt(LocalDateTime.now());
      usersList.add(user);
    }

    // usersMapper.batchInsertOrUpdate(usersList);
    Integer sum =
        BatchExecHelper.batchExec(usersList, usersExtendMapper::batchInsertOrUpdate, Users.class);
    System.out.println("sum: " + sum);

    QueryWrapper<Users> wrapper = new QueryWrapper<>();
    var count = usersMapper.selectCount(wrapper);
    Assert.assertEquals(Integer.valueOf(max), count);
    Assert.assertEquals(Integer.valueOf(max), sum);

    wrapper.eq(UsersFieldNames.userName, usersList.get(0).getUserName());
    var li = usersMapper.selectList(wrapper);
    Assert.assertNotEquals(0, li.size());
  }
}
