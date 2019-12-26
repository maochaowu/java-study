package com.mcw.cora.spring.annotation.config.scan;

/**
 * @Author yibi
 * Date 2019/8/18 11:05
 * Version 1.0
 **/
public class DemoEvent {

    public boolean type;

    public DemoEvent(boolean type) {
        this.type = type;
    }

    public void say() {
        System.out.println(type + " 发布了事件");
    }
}
