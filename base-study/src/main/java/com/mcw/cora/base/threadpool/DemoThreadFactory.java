package com.mcw.cora.base.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author yibi
 * Date 2019/8/13 21:06
 * Version 1.0
 **/
public class DemoThreadFactory implements ThreadFactory {

    private static final AtomicLong idGenerator = new AtomicLong(0);

    private static final String THREAD_PREFIX = "DEMO_";

    @Override
    public Thread newThread(Runnable r) {
        String threadName = THREAD_PREFIX + idGenerator.incrementAndGet();
        Thread thread = new Thread(r, threadName);
//        thread.setUncaughtExceptionHandler((t, e) -> {
//            System.out.println(t.getName() + " " + e.getLocalizedMessage());
//        });
        return thread;
    }
}
