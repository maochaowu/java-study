package com.mcw.cora.spring.annotation.config.scan;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @Author yibi
 * Date 2019/8/18 20:57
 * Version 1.0
 **/
@Component("demoScopeBean")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DemoScopeBean {
}
