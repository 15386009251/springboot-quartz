package com.quartz.demo.common;

import java.util.UUID;

/**
 * Created by LIU on 2018/10/9 15:33
 */
public class ThreadId {
    private static ThreadLocal<String> threadLocal = new ThreadLocal();

    public ThreadId() {
    }

    public static String getThreadId() {
        if(threadLocal.get() == null) {
            threadLocal.set(String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        }

        return (String)threadLocal.get();
    }

    public static void setThreadId(String threadId) {
        threadLocal.set(threadId);
    }

    public static void setThreadIdWithIP(String ip) {
        threadLocal.set(ip + ":" + UUID.randomUUID().getLeastSignificantBits());
    }
}
