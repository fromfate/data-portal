package com.asiainfo.monitor.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: LiuJH
 * @Date: 2020-02-27
 * @Description:
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean
    public Executor sjztExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程
        executor.setCorePoolSize(10);
        // 最大线程
        executor.setMaxPoolSize(20);
        // 缓存队列
        executor.setQueueCapacity(10000);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名前缀
        executor.setThreadNamePrefix("sjzt_async_thread");
        executor.initialize();
        return executor;
    }
}
