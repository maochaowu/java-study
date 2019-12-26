package com.mcw.cora.distribute.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.support.locks.LockRegistry;
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
    //@Qualifier(value = "jdbcLockRegistry")
    @Qualifier(value = "redisLockRegistry")
    //@Qualifier(value = "zookeeperLockRegistry")
    private LockRegistry lockRegistry;

    @Test
    public void testRedisLock() {
        Lock lock = lockRegistry.obtain("subKey");
        lock.lock();
        try {
            System.out.println("锁定成功");
            Thread.sleep(1000 * 20);
            System.out.println("休眠完成");
        } catch (Exception e) {

        } finally {
            lock.unlock();
            System.out.println("锁释放成功");
        }
    }
}
