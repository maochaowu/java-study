package com.mcw.cora.redis.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @Author yibi
 * Date 2019/6/5 20:47
 * Version 1.0
 **/
public class ClusterRedisTest {
    public static void main(String[] args) {
        JedisCluster cluster = new JedisCluster(new HostAndPort("192.168.200.137", 7000));
        System.out.println(cluster.get("str"));
    }
}
