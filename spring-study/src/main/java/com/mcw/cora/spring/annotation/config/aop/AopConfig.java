package com.mcw.cora.spring.annotation.config.aop;

import com.mcw.cora.spring.annotation.config.aop.demo.DefaultAsyncConfigurer;
import com.mcw.cora.spring.annotation.config.aop.demo.AsyncService;
import com.mcw.cora.spring.annotation.config.aop.demo.Demo;
import com.mcw.cora.spring.annotation.config.aop.demo.DemoAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author yibi
 * Date 2019/9/1 13:41
 * Version 1.0
 **/
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
public class AopConfig {

    @Bean
    public AopAspectDemo aopAspectDemo() {
        return new AopAspectDemo();
    }

    @Bean
    public Demo demo() {
        return new Demo();
    }

    @Bean
    public AsyncService asyncService() {
        return new AsyncService();
    }

    @Bean
    public DefaultAsyncConfigurer defaultAsyncConfigurer() {
        return new DefaultAsyncConfigurer();
    }

    public DemoAdvisor demoAdvisor() {
        return new DemoAdvisor();
    }

}
