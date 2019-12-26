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
@ContextConfiguration(classes = {MoreThreadDistributeLockTest.class})
@ComponentScan(basePackages = "com.mcw.cora.distribute.lock")
public class MoreThreadDistributeLockTest {

    @Autowired
    @Qualifier(value = "jdbcLockRegistry")
    //@Qualifier(value = "redisConnectionFactory")
    //@Qualifier(value = "zookeeperLockRegistry")
    private LockRegistry lockRegistry;

    @Test
    public void testRedisLock() {
        Thread tt1 = new Thread(() -> {
            Lock lock = lockRegistry.obtain("subKey");
            lock.lock();
            try {
                System.out.printf("线程%s 锁定成功,\n", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }, "tt1");
        tt1.start();

        Thread tt2 = new Thread(() -> {
            Lock lock = lockRegistry.obtain("subKey");
            lock.lock();
            try {
                System.out.printf("线程%s 锁定成功,\n", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }, "tt2");
        tt2.start();

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
