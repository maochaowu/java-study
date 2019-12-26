package com.mcw.cora.spring.annotation.config.aop.demo;

/**
 * @Author yibi
 * Date 2019/9/1 13:49
 * Version 1.0
 **/
public class Demo implements DemoInterface {

    @Override
    public DemoInterface doSomething(String name) {
        System.out.println("demo do" + name);
        int i = 1 / 0;
        return this;
    }
}
