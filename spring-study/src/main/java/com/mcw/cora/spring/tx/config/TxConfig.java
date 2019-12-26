package com.mcw.cora.spring.tx.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.mcw.cora.spring.tx.entity.TxUser;
import com.mcw.cora.spring.tx.mapper.TxUserMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *@desc
 *@author mcw
 *@date 2019/9/14
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public class TxConfig {

    @Bean
    public TxUserService txUserService() {
        return new TxUserService();
    }

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://192.168.200.150:3306/tx_test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, ConfigurableApplicationContext context) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new Resource[0];
        try {
            resources = context.getResources("classpath*:mappers/*.xml");
        } catch (IOException e) {

        }
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.mcw.cora.spring.tx.mapper");
        return mapperScannerConfigurer;
    }

    /**
     * @desc: 配置事务管理器
     * @author: mcw
     * @date: 2019/9/15
     * @param: dataSource
     * @return: org.springframework.transaction.PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager dataSourceTx(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @TransactionalEventListener
    public void transactionListener(ApplicationEvent event) {
        System.out.println("接收到事件通知,事件内容为" + JSON.toJSONString(event));
    }

    @Transactional(rollbackFor = Exception.class, timeout = 4)
    public class TxUserService {
        @Autowired
        private TxUserMapper txUserMapper;

        @Transactional(rollbackFor = Exception.class)
        public Integer insert(TxUser txUser) {
            int count = txUserMapper.insert(txUser);
            txUser.setName("王五");
            Object obj = AopContext.currentProxy();
            TxUserService service = (TxUserService) obj;
            service.update(txUser);
            return count;
        }

        @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
        public Integer update(TxUser txUser) {
            int count = txUserMapper.updateByPrimaryKeySelective(txUser);
            //int mod = 2 / 0;
            return count;
        }
    }
}
