package com.mcw.cora.spring.annotation.config;

import com.mcw.cora.spring.annotation.config.scan.DemoComponentBean;
import com.mcw.cora.spring.annotation.config.scan.DemoScopeBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yibi
 * Date 2019/7/15 20:41
 * Version 1.0
 **/
public class AnnotationConfigBootStrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(ImportConfiguration.class);
        configApplicationContext.refresh();

        DemoComponentBean compBean = configApplicationContext.getBean(DemoComponentBean.class);
        System.out.println(compBean);


        DemoScopeBean scopeBean = configApplicationContext.getBean(DemoScopeBean.class);
        //configApplicationContext.publishEvent(new DemoEvent(true));
    }
}
