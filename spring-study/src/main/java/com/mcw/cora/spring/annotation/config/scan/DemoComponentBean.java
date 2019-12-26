package com.mcw.cora.spring.annotation.config.scan;

import com.mcw.cora.spring.annotation.config.DemoMethodBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @Author yibi
 * Date 2019/8/14 9:53
 * Version 1.0
 **/
@Component("demoComponentBean")
public class DemoComponentBean implements Serializable {

    private static final long serialVersionUID = 5248181942068104664L;

    private DemoMethodBean demoMethodBean;
    @Autowired
    private List<DemoScopeBean> demoScopeBeans;

    @Value("${applicaiton.dmeo.name}")
    private String appName;

    public DemoComponentBean() {

    }

    @PostConstruct
    public void init() {
        System.out.println("我被初始化了。。。");
    }

    @Override
    public String toString() {
        return "DemoComponentBean{" +
                "demoMethodBean=" + demoMethodBean +
                ", demoScopeBeans=" + demoScopeBeans +
                ", appName='" + appName + '\'' +
                '}';
    }

    public DemoMethodBean getDemoMethodBean() {
        return demoMethodBean;
    }

    @Required
    @Resource(type = DemoMethodBean.class)
    public void setDemoMethodBean(DemoMethodBean demoMethodBean) {
        this.demoMethodBean = demoMethodBean;
    }
}
