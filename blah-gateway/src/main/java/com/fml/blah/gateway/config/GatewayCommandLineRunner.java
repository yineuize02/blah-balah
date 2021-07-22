package com.fml.blah.gateway.config;

import cn.hutool.core.collection.CollUtil;
import com.fml.blah.common.constants.RedisConstants;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class GatewayCommandLineRunner implements CommandLineRunner {
  @Autowired private RedisTemplate redisTemplate;

  @Override
  public void run(String... args) throws Exception {
    Map<String, List<String>> resourceRolesMap = new TreeMap<>();
    resourceRolesMap.put("GET_/blah-api/hello/{id}", CollUtil.toList("ADMIN"));
    resourceRolesMap.put("GET_/blah-api/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
    resourceRolesMap.put("GET_/blah-seckill/hello", CollUtil.toList("ADMIN", "TEST"));
    resourceRolesMap.put("POST_/blah-seckill/seckill", CollUtil.toList("ADMIN", "TEST"));

    redisTemplate.opsForHash().putAll(RedisConstants.RESOURCE_ROLES_MAP, resourceRolesMap);
  }
}
