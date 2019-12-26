package com.mcw.cora;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
@NacosPropertySource(dataId = "first-demo-prop", groupId = "DEFAULT_GROUP", autoRefreshed = true)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
