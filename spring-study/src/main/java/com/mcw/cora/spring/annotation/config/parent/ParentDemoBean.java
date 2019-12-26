package com.mcw.cora.spring.annotation.config.parent;

import com.mcw.cora.spring.annotation.config.scan.DemoEventListenerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yibi
 * Date 2019/8/18 12:31
 * Version 1.0
 **/
@Configuration
public class ParentDemoBean {

    @Bean
    public DemoEventListenerBean demoEventListenerBean() {
        return new DemoEventListenerBean();
    }
}
