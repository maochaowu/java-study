package com.mcw.cora.spring.tx;

import com.mcw.cora.spring.tx.config.LazyConfig;
import com.mcw.cora.spring.tx.config.TxConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *@desc
 *@author mcw
 *@date 2019/9/14
 */
public class TxBootstrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        configApplicationContext.register(TxConfig.class,LazyConfig.class);
        configApplicationContext.refresh();

        LazyConfig config = configApplicationContext.getBean(LazyConfig.class);
        System.out.println(config.demoBean);
        System.out.println(config.demoBean);

//        TxConfig.TxUserService txUserService = configApplicationContext.getBean(TxConfig.TxUserService.class);
//        TxUser user = new TxUser();
//        user.setName("张三");
//        user.setGender(true);
//        user.setBirthday("2019-10-29");
//        user.setCreateDate(new Date());
//        user.setAddress("北京市天都街");
//        int count = txUserService.insert(user);
//        System.out.println("执行insert操作，返回结果为=" + count);
    }
}
