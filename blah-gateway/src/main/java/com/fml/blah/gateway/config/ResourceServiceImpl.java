package com.fml.blah.gateway.config;

import cn.hutool.core.collection.CollUtil;
import com.fml.blah.common.constants.RedisConstants;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl {

  @Autowired private RedisTemplate redisTemplate;

  @PostConstruct
  public void initData() {
    Map<String, List<String>> resourceRolesMap = new TreeMap<>();
    resourceRolesMap.put("GET_/blah-api/hello", CollUtil.toList("ADMIN"));
    resourceRolesMap.put("GET_/blah-api/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
    redisTemplate.opsForHash().putAll(RedisConstants.RESOURCE_ROLES_MAP, resourceRolesMap);
  }
}
