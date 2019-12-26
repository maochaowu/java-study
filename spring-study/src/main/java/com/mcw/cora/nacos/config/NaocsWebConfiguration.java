package com.mcw.cora.nacos.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Author yibi
 * Date 2019/7/4 22:04
 * Version 1.0
 **/
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.mcw.cora.nacos.config")
public class NaocsWebConfiguration {
}
