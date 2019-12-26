package com.mcw.cora.spring.annotation.config.aop;

import com.mcw.cora.spring.annotation.config.aop.demo.AsyncService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author yibi
 * Date 2019/9/1 13:40
 * Version 1.0
 **/
public class AopAnnotationConfigBootStrap {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(AopConfig.class);
        configApplicationContext.refresh();

        AsyncService asyncService = configApplicationContext.getBean(AsyncService.class);
        Future<String> name = asyncService.getUserName();
        System.out.println(name.get() + " " + Thread.currentThread().getName());
    }
}
