package com.mcw.cora.spring.annotation.config.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author yibi
 * Date 2019/9/1 13:41
 * Version 1.0
 **/
@Aspect
public class AopAspectDemo {

    @Before(value = "execution( * com.mcw.cora.spring.annotation.config.aop.demo.*.*(..)) && args(name)")
    public void before(String name) {
        System.out.println("before method execute " + name);
    }


    @AfterThrowing(value = "execution( * com.mcw.cora.spring.annotation.config.aop.demo.*.*(..))", throwing = "e")
    public void afterThrowing(Exception e) {
        System.out.println("after throwing method execute " + e);
    }
}
