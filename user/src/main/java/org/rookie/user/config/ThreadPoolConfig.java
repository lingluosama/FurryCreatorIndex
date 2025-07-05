package org.rookie.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.lang.Thread.UncaughtExceptionHandler;
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Bean(name = "redisTaskExecutor")
    public ThreadPoolTaskExecutor redisTaskExecutor() {
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(6);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(500);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("RedisTaskExecutor-");
        executor.initialize();
        
        return executor;
        
    }
    
    
    
}
