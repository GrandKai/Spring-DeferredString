package com.spring.deferred.result.springdeferredresult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringDeferredResultApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(SpringDeferredResultApplication.class, args);
  }

  // 配置异步支持，设置了一个用来异步执行业务逻辑的工作线程池，设置了默认的超时时间是60秒
  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    asyncSupportConfigurer.setTaskExecutor(threadPoolTaskExecutor());
    asyncSupportConfigurer.setDefaultTimeout(60 * 1000L);
  }

  @Bean
  public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(25);
    executor.setQueueCapacity(100);
    return executor;
  }
}
