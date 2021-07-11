package com.fml.blah.store.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    // 核心线程数
    taskExecutor.setCorePoolSize(8);
    // 最大线程数
    taskExecutor.setMaxPoolSize(16);
    // 队列大小
    taskExecutor.setQueueCapacity(100);
    taskExecutor.initialize();
    return taskExecutor;
  }
}
