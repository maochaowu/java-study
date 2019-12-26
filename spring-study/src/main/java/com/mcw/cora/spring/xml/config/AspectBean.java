package com.mcw.cora.spring.xml.config;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author yibi
 * Date 2019/8/19 18:47
 * Version 1.0
 **/
public class AspectBean {

    public void before() {
        System.out.println("before前置拦截");
    }

    public void around(ProceedingJoinPoint joinPoint) {
        System.out.println("around前置拦截开始");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println(throwable);
        }
        System.out.println("around后置拦截开始");
    }

}
