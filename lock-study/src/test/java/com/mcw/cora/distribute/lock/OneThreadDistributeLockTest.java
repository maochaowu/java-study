package com.mcw.cora.distribute.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.locks.Lock;

/**
 * @Author yibi
 * Date 2019/5/19 20:16
 * Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OneThreadDistributeLockTest.class})
@ComponentScan(basePackages = "com.mcw.cora.distribute.lock")
public class OneThreadDistributeLockTest {

    @Autowired
    @Qualifier(value = "jdbcLockRegistry")
    //@Qualifier(value = "redisConnectionFactory")
    //@Qualifier(value = "zookeeperLockRegistry")
    private LockRegistry lockRegistry;

    @Test
    public void testRedisLock() {
        Lock lock = lockRegistry.obtain("subKey");
        lock.lock();
        System.out.println("锁定成功");
        lock.unlock();
    }
}
