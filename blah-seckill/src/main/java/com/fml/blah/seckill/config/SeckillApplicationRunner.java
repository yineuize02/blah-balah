package com.fml.blah.seckill.config;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeckillApplicationRunner implements ApplicationRunner {

  @Autowired private SeckillConfig seckillConfig;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    //    ClassPathResource classPathResource = new ClassPathResource("predecr.lua");
    //    InputStream inputStream = classPathResource.getInputStream();
    // String result = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));

    String lua = ResourceUtil.readUtf8Str("predecr.lua");
    seckillConfig.setPreDecrLua(lua);
  }
}
