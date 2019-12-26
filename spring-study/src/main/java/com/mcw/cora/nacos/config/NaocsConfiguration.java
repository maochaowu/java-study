package com.mcw.cora.nacos.config;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yibi
 * Date 2019/7/4 21:49
 * Version 1.0
 **/
@Configuration
@EnableNacos(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
@NacosPropertySource(dataId = "first-demo-prop", autoRefreshed = true)
public class NaocsConfiguration {
}
