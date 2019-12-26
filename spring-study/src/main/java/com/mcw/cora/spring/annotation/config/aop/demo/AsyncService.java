package com.mcw.cora.spring.annotation.config.aop.demo;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *@desc
 *@author mcw
 *@date 2019/9/28
 */
public class AsyncService {

    @Async
    public Future<String> getUserName() {
        System.out.println(Thread.currentThread().getName());
        return new Future<String>(){

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return "LiLei";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
    }
}
