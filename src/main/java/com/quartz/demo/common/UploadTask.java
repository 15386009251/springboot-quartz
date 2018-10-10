package com.quartz.demo.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by LIU on 2018/10/9 14:38
 */
public class UploadTask extends AbstractJob {

    private ThreadPoolExecutor threadPool;

    @Override
    protected boolean proDO() {
        ThreadPoolTaskExecutor taskExecutor =
                (ThreadPoolTaskExecutor) SpringContextHolder.getBean("orderThreadPool");
        try {
            threadPool = taskExecutor.getThreadPoolExecutor();
        } catch (Exception e) {
            System.out.println("定时任务前置异常");
            return false;
        }
        return true;
    }

    @Override
    protected void doTask() {
        threadPool.execute(() ->
            {
                System.out.println("执行的任务开始");
                System.out.println("正在执行任务");
                System.out.println("执行的任务结束");
            }
        );
    }

    @Override
    protected void afterDO() {
        super.afterDO();
    }
}
