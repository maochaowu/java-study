package com.mcw.cora.spring.xml.config;

import com.mcw.cora.spring.xml.config.aop.AopDemo;
import com.mcw.cora.spring.xml.config.aop.Monitor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author yibi
 * Date 2019/7/15 20:41
 * Version 1.0
 **/
public class XmlConfigBootStrap {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext();
        xmlApplicationContext.setConfigLocation("classpath:iocConfig.xml");
        xmlApplicationContext.refresh();

        AopDemo aopDemo = xmlApplicationContext.getBean(AopDemo.class);
        aopDemo.doSomething();
        Monitor monitor = (Monitor) aopDemo;
        monitor.start();
    }
}
