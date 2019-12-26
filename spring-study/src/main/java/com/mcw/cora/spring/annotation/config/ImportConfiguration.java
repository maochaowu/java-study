package com.mcw.cora.spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author yibi
 * Date 2019/7/15 20:29
 * Version 1.0
 **/
@Configuration
@PropertySource(value = "classpath:config.properties")
@ComponentScan(basePackages = "com.mcw.cora.spring.annotation.config.scan")
public class ImportConfiguration {

    @Bean
    public DemoMethodBean getMethodBean() {
        return new DemoMethodBean();
    }
}
