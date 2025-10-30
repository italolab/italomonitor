package com.redemonitor.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    ThreadPoolTaskScheduler schedulerExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize( 16 );
        scheduler.setThreadNamePrefix( "disp-monitor-" );
        scheduler.setRemoveOnCancelPolicy( true );
        scheduler.setWaitForTasksToCompleteOnShutdown( true );
        scheduler.initialize();
        return scheduler;
    }

}
