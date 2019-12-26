package com.mcw.cora.spring.tx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 *@author mcw
 *@date 2019/12/1
 */
@Configuration
@Import(DemoBean.class)
public class LazyConfig {

    @Autowired
    @Lazy
    public DemoBean demoBean;

}

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class DemoBean {

}
