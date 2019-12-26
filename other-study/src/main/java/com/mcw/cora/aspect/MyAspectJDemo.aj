package com.mcw.cora.aspect;

/**
 *@desc AspectJ 示例
 *@author mcw
 *@date 2019/9/4
 */
public aspect MyAspectJDemo {
    pointcut printCost():call(* com.mcw.cora.aspect.HelloWord.sayHello(..));

    Object around():printCost(){
        System.out.println("开始记录时间" + System.currentTimeMillis());
        Object result = proceed();
        System.out.println("结束记录时间" + System.currentTimeMillis());
        return result;
    }
}
