package com.dsmh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author: Asher
 * @date: 2022/4/25 22:48
 * @description:
 */
@Configuration
public class AsyncThreadPollConfiguration {

    @Bean("ginsengThreadPool")
    public ThreadPoolTaskExecutor ginsengThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(32);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ginsengthread-");
        executor.initialize();
        return executor;
    }
}
