package com.example.dividen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

        int corePoolSize = Runtime.getRuntime().availableProcessors();

        taskScheduler.setPoolSize(corePoolSize);
        taskScheduler.initialize();

        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
