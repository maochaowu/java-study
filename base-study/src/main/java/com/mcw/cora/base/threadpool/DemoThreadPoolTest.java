package com.mcw.cora.base.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author yibi
 * Date 2019/8/13 21:12
 * Version 1.0
 **/
public class DemoThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new DemoThreadPoolExecutor(2, 8, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100), new DemoThreadFactory());

        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                System.out.printf("线程[%s]开始执行", Thread.currentThread().getName());
                System.out.println();
               try{
                   int j = 10 / 0;
               }catch (Exception e){
                   System.out.println(e.getLocalizedMessage());
               }
            });
        }
        Thread.sleep(1000);
        executor.shutdown();
    }
}
