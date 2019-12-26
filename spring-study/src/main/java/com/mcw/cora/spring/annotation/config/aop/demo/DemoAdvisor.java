package com.mcw.cora.spring.annotation.config.aop.demo;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *@desc
 *@author mcw
 *@date 2019/9/10
 */
public class DemoAdvisor extends AbstractPointcutAdvisor {

    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {

            @Override
            public ClassFilter getClassFilter() {
                return clazz ->
                        clazz.isAssignableFrom(Demo.class);
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {

                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return true;
                    }

                    @Override
                    public boolean isRuntime() {
                        return true;
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        return true;
                    }
                };
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return (MethodInterceptor) invocation -> {
            Field field = invocation.getClass().getDeclaredField("methodProxy");
            field.setAccessible(true);
            MethodProxy proxy = (MethodProxy) field.get(invocation);
            System.out.println(proxy);
            System.out.println("demo advisor 执行");
            return invocation.proceed();
        };
    }
}
