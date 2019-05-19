package com.mcw.cora.distribute.lock.zookeeepr;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 * @Author yibi
 * Date 2019/5/19 21:25
 * Version 1.0
 **/
@Configuration
@PropertySource(value = "application.properties")
public class ZookeeperDisLockConfiguration {

    @Value("${zk.server.url}")
    private String zkServerUrl;
    @Value("${connection.timeout}")
    private Integer connectionTimeOut;

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        builder.connectString(zkServerUrl).connectionTimeoutMs(connectionTimeOut).retryPolicy(new RetryOneTime(1000));
        CuratorFramework client = builder.build();
        client.start();
        return client;
    }

    @Bean("zookeeperLockRegistry")
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework client) {
        return new ZookeeperLockRegistry(client);
    }
}
