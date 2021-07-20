package com.fml.blah.seckill.config;

import cn.hutool.core.io.resource.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LuaApplicationRunner implements ApplicationRunner {

  @Autowired private LuaConfig luaConfig;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    //    ClassPathResource classPathResource = new ClassPathResource("predecr.lua");
    //    InputStream inputStream = classPathResource.getInputStream();
    // String result = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));

    String lua = ResourceUtil.readUtf8Str("predecr.lua");
    luaConfig.setPreDecrLua(lua);
  }
}
