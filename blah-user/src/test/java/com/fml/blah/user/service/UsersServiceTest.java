package com.fml.blah.user.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fml.blah.common.redis.RedisUtils;
import com.fml.blah.common.utils.BatchExecHelper;
import com.fml.blah.test.ServiceTestBase;
import com.fml.blah.user.entity.Users;
import com.fml.blah.user.mapper.UsersMapper;
import com.fml.blah.user.mapper_extend.UsersExtendMapper;
import com.fml.blah.user.service.UsersService.UserAddParam;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class UsersServiceTest extends ServiceTestBase {
  @Autowired private UsersMapper usersMapper;
  @Autowired private UsersExtendMapper usersExtendMapper;
  @Autowired private UsersService usersService;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private RedissonClient redissonClient;

  @Autowired private RedisTemplate<String, Object> redisTemplate;
  @Autowired private RedisUtils redisUtils;

  @EqualsAndHashCode(callSuper = true)
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  static class RedisDTO extends Users {
    private Integer ii;
    private Long ll;
    private BigDecimal bd;
    private LocalDateTime time;
    private LocalDate date;
    private String name;
    private List<RedisDTO> list;
    private Map<String, RedisDTO> map;
  }

  @Test
  public void redisUtilTest() {
    var time = System.currentTimeMillis();
    Users user1 = new Users();
    user1.setId(1L);
    user1.setPassword("foo_" + time);
    user1.setUserName("bar_" + time);
    user1.setCreatedAt(LocalDateTime.now());

    RBucket<Users> bucket = redissonClient.getBucket("user1");
    bucket.set(user1);

    var result = bucket.get();
    Assert.assertNotNull(result);
    log.info(result.toString());

    redisTemplate.opsForValue().set("user2", user1);
    var obj = redisTemplate.opsForValue().get("user2");
    log.info(obj.toString());

    var redisDto0 =
        RedisDTO.builder()
            .bd(new BigDecimal("100.0092"))
            .date(LocalDate.now())
            .ii(22)
            .ll(33L)
            .list(new ArrayList<>())
            .name("xyz")
            .time(LocalDateTime.MAX)
            .build();
    redisDto0.setUserName("aaa");
    redisDto0.setId(99L);
    redisDto0.setCreatedAt(LocalDateTime.now());

    var redisDto1 = BeanUtil.copyProperties(redisDto0, RedisDTO.class);
    redisDto1.setList(new ArrayList<>(List.of(redisDto0, redisDto0)));
    redisDto1.setMap(new HashMap<>(Map.of("dtoRBucket", redisDto0, "r2", redisDto0)));
    RBucket<RedisDTO> dtorBucket = redissonClient.getBucket("dtoRBucket");
    dtorBucket.set(redisDto1);
    var rr = dtorBucket.get();
    Assert.assertNotNull(rr);
    log.info(rr.toString());

    redisTemplate.opsForValue().set("dtoRedisTemplate", redisDto1);
    var rr2 = redisTemplate.opsForValue().get("dtoRedisTemplate");
    log.info(rr2.toString());

    Assert.assertNotNull(rr2);

    redisUtils.set("dtoRedisUtils", redisDto1);
    var rr3 = redisUtils.get("dtoRedisUtils");
    log.info(rr3.toString());

    Assert.assertNotNull(rr3);
  }

  @Test
  public void testCache() {
    jdbcTemplate.execute("truncate table blah_user.users;");
    var param = new UserAddParam();
    param.setPassword("123456");
    param.setUserName("222");

    var u = usersService.addUser(param);
    Assert.assertNotNull(u.getId());
    var info = usersService.getUserInfoByName(param.getUserName());
    Assert.assertNotNull(info);
    jdbcTemplate.execute("truncate table blah_user.users;");

    var infoCache = usersService.getUserInfoByName(param.getUserName());
    Assert.assertNotNull(infoCache);
  }

  @Test
  public void redissonLockTest() {
    var lock = redissonClient.getLock("lock_bar");
    lock.lock(2L, TimeUnit.MINUTES);
    var tryCurrent = lock.tryLock();
    Assert.assertTrue(tryCurrent);
    lock.unlock();

    var t =
        new Thread(
            () -> {
              var tryOther = lock.tryLock();
              Assert.assertFalse(tryOther);
            });
    t.start();

    try {
      t.join();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  @Test
  public void redissonLockTest2() {
    var lock = redissonClient.getLock("lock_foo");
    lock.lock(2L, TimeUnit.MINUTES);
    var tryCurrent = lock.tryLock();
    Assert.assertTrue(tryCurrent);
    lock.unlock();

    CountDownLatch countDownLatch = new CountDownLatch(1);
    ExecutorService executor = Executors.newFixedThreadPool(1);

    executor.submit(
        () -> {
          var tryOther = lock.tryLock();

          Assert.assertFalse(tryOther);
          countDownLatch.countDown();
        });
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    lock.unlock();
  }

  @Test
  public void testBatchUpdate() {
    jdbcTemplate.execute("truncate table blah_user.users;");

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

    var lambdaQueryWrapper =
        Wrappers.<Users>lambdaQuery().eq(Users::getPassword, user3.getPassword());
    var u3r = usersMapper.selectOne(lambdaQueryWrapper);
    Assert.assertEquals(user3.getUserName(), u3r.getUserName());
  }

  // @Test
  public void testMax() {

    jdbcTemplate.execute("truncate table blah_user.users;");
    int max = 30011;
    List<Users> usersList = new ArrayList<>(max);

    for (int i = 0; i < max; i++) {
      var time = System.currentTimeMillis();
      var user = new Users();
      user.setId(Integer.toUnsignedLong(i + 1));
      user.setUserName("foo" + i + time);
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

    var liw = Wrappers.<Users>lambdaQuery().eq(Users::getUserName, usersList.get(0).getUserName());
    var li = usersMapper.selectList(liw);
    Assert.assertNotEquals(0, li.size());
  }

  @Test
  public void testSelect() {
    jdbcTemplate.execute("truncate table blah_user.users;");
    var users = usersMapper.selectBatchIds(List.of(10000L));
    Assert.assertNotNull(users);

    var user = usersMapper.selectById(100000);
    Assert.assertNull(user);

    var user2 = usersMapper.selectOne(Wrappers.<Users>lambdaQuery().eq(Users::getId, 10000));
    Assert.assertNull(user2);
    var uin = new Users();
    uin.setId(999L);
    uin.setPassword("aa");
    uin.setUserName("bb");
    var ri = usersMapper.insert(uin);
    Assert.assertEquals(1, ri);
    var xx = usersMapper.selectBatchIds(List.of(9));
    Assert.assertEquals(0, xx.size());
  }

  @Autowired private PasswordEncoder passwordEncoder;

  @Test
  public void testSave() {
    jdbcTemplate.execute("truncate table blah_user.users;");

    var param = new UserAddParam();
    param.setPassword("123456");
    param.setUserName("222");
    var u = usersService.addUser(param);
    Assert.assertNotNull(u.getId());

    var u2 = new Users();
    u2.setUserName("5599");
    u2.setPassword("xxxcccc");
    usersMapper.insert(u2);
    System.out.println("usersMapper.insert(u2); " + u2.getId());
  }

  @Test
  public void testMatch() {
    var match =
        passwordEncoder.matches(
            "123456", "$2a$10$ue0uFp4wloH8DqRLlGhjOeogbo/MXzQfZkSgn0K.3zZICyRQ49z0O");
    Assert.assertTrue(match);

    match =
        passwordEncoder.matches(
            "123456", "$2a$10$XUWsJ0kg1ApZ4b4v6aqiGOzQ1s29o4325cCcMRsug5XANV8jGDipC");
    Assert.assertTrue(match);

    match =
        passwordEncoder.matches(
            "123456", "$2a$10$HD32EvfZKKnBXcFbRX30e.Ju.GuBYASKq/oLPuHtWHXxrOz4ah4mi");
    Assert.assertTrue(match);
  }
}
