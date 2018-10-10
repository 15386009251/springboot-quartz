package com.quartz.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by LIU on 2018/10/9 15:33
 */
@Configuration
public class QuartzJobAutoConfig {

    @Autowired
    QuartzScheduleService quartzScheduleService;

    @Bean
    public Object jobDetailGenerates(){
        QuartzTaskInfo uploadTaskJob = new QuartzTaskInfo();
        uploadTaskJob.setCronExpression("*/10 * * * * ?");
        uploadTaskJob.setJobName(UploadTask.class.getName());
        uploadTaskJob.setJobGroup("tradeJobGroup");
        initJobs(uploadTaskJob);
        return new Object();
    }

    private void initJobs(QuartzTaskInfo taskInfo) {
        try {
            if (quartzScheduleService.checkExists(taskInfo.getJobName(), taskInfo.getJobGroup())) {
                quartzScheduleService.edit(taskInfo);
            } else {
                quartzScheduleService.addJob(taskInfo);
            }
            System.out.println("=======自动初始job成功：{}============");
        } catch (BusinessException e) {
            System.out.println("initJobs failed");
        }
    }

    @Bean(name = "orderThreadPool")
    public ThreadPoolTaskExecutor getThreadTask(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(100);

        executor.setMaxPoolSize(500);

        executor.setQueueCapacity(1000);

        executor.setKeepAliveSeconds(300);

        executor.setThreadNamePrefix("orderThread");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;

    }

    /**
     * 系统割接数据导换线程池
     *
     * @return
     */
    @Bean(name = "cutoverThreadPool")
    public ThreadPoolTaskExecutor getCutoverThreadTask()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：线程池维护线程的最少数量
        executor.setCorePoolSize(30);
        // 最大线程数
        executor.setMaxPoolSize(100);
        // 队列大小
        executor.setQueueCapacity(1000);
        // 空闲线程回收时间间隔
        executor.setKeepAliveSeconds(300);
        // 线程名称前缀
        executor.setThreadNamePrefix("Cutover-Thread");
        // 对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
