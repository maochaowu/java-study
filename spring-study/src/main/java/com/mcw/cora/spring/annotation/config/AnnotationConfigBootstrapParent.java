package com.mcw.cora.spring.annotation.config;

import com.mcw.cora.spring.annotation.config.scan.DemoComponentBean;
import com.mcw.cora.spring.annotation.config.scan.DemoEvent;
import com.mcw.cora.spring.annotation.config.parent.ParentDemoBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yibi
 * Date 2019/8/18 12:29
 * Version 1.0
 **/
public class AnnotationConfigBootstrapParent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContextParent = new AnnotationConfigApplicationContext();
        String beanName = "parentDemoBean";
        BeanDefinition db = new RootBeanDefinition();
        ((RootBeanDefinition) db).setBeanClass(ParentDemoBean.class);
        configApplicationContextParent.registerBeanDefinition(beanName, db);
        configApplicationContextParent.refresh();


        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.setParent(configApplicationContextParent);
        configApplicationContext.register(ImportConfiguration.class);
        configApplicationContext.refresh();

        DemoComponentBean compBean = configApplicationContext.getBean(DemoComponentBean.class);
        System.out.println(compBean);

        configApplicationContext.publishEvent(new DemoEvent(true));
    }
}
