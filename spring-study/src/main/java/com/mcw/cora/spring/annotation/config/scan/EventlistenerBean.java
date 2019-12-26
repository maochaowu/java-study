package com.mcw.cora.spring.annotation.config.scan;

import org.springframework.context.event.EventListener;

/**
 * @Author yibi
 * Date 2019/8/18 11:04
 * Version 1.0
 **/
public interface EventlistenerBean {

    @EventListener(condition = "args[0].type")
    void listener(DemoEvent event);
}
