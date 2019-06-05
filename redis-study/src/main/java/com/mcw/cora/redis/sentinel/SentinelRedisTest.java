package com.mcw.cora.redis.sentinel;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author yibi
 * Date 2019/6/2 21:22
 * Version 1.0
 **/
public class SentinelRedisTest {

    public static void main(String[] args) {
        Set<String> sentinelConfig = new HashSet<>();
        sentinelConfig.add(new HostAndPort("192.168.200.150", 26379).toString());
        sentinelConfig.add(new HostAndPort("192.168.200.137", 26379).toString());
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster1", sentinelConfig);
        Jedis master = sentinelPool.getResource();
        master.set("name", "张三");
        System.out.println(master.get("name"));
    }
}
