package com.mcw.cora.spring.xml.config.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @Author yibi
 * Date 2019/9/1 8:15
 * Version 1.0
 **/
public class IntroductionDemo implements IntroductionAdvisor, Monitor {

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return true;
            }
        };
    }

    @Override
    public void validateInterfaces() throws IllegalArgumentException {

    }

    @Override
    public Advice getAdvice() {
        return new DelegatingIntroductionInterceptor(this);
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    @Override
    public Class<?>[] getInterfaces() {
        return new Class[]{Monitor.class};
    }

    @Override
    public IntroductionDemo start() {
        return this;
    }
}
