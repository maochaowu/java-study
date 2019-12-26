package com.mcw.cora.spring.annotation.config.scan;

import org.springframework.stereotype.Component;

/**
 * @Author yibi
 * Date 2019/8/18 10:57
 * Version 1.0
 **/
@Component
public class DemoEventListenerBean implements EventlistenerBean {

    @Override
    public void listener(DemoEvent event) {
        event.say();
    }
}
