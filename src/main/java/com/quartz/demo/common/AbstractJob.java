package com.quartz.demo.common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by LIU on 2018/10/9 15:01
 */
public abstract class AbstractJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        boolean pro = proDO();
        System.out.println(getClass().getSimpleName() + ":前置任务完成...result:" + pro);
        if(pro) {
            doTask();
            System.out.println(getClass().getSimpleName() + ":执行任务完成...");
            afterDO();
            System.out.println(getClass().getSimpleName() + ":后置任务完成...");
        }
    }

    /**
     * 具体任务内容，该方法不要抛出任何异常，全部内部捕获
     */
    protected abstract void doTask();

    /**
     * 定时任务前置工作，返回true则开始执行任务，返回false则终止本次任务。
     * 基类中空实现，默认返回true，如子类业务需要，重写该方法即可
     */
    protected boolean proDO() {
        return true;
    }

    /**
     * 定时任务后置工作，处理任务执行后需要做的扫尾工作。
     * 基类中空实现，如子类业务需要，重写该方法即可
     */
    protected void afterDO() {

    }
}
