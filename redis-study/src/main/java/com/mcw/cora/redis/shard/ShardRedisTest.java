package com.mcw.cora.redis.shard;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yibi
 * Date 2019/6/2 14:13
 * Version 1.0
 * 分片存储----通过一致性hash，当不指定权重时，每个shardInfo会均分160份。
 **/
public class ShardRedisTest {
    public static void main(String[] args) {
        //定义redis的配置
        GenericObjectPoolConfig poolconfig = new GenericObjectPoolConfig();
        poolconfig.setMaxTotal(1000);
        poolconfig.setMinIdle(5);
        //定义redis的多个节点机器
        List<JedisShardInfo> list = new ArrayList<>();
        //为集合添加参数
        list.add(new JedisShardInfo("192.168.200.150", 6379));
        list.add(new JedisShardInfo("192.168.200.150", 6380));
        list.add(new JedisShardInfo("192.168.200.150", 6381));
        //定义redis分片连接池
        ShardedJedisPool jedisPool = new ShardedJedisPool(poolconfig, list);
        //获取连接操作redis
        ShardedJedis shardedJedis = jedisPool.getResource();
        //向redis中添加20个记录查看分片结果
        for (int i = 0; i < 10; i++) {
            //增加的记录格式为   key=NUM_i   value=i
            shardedJedis.set("NUM_" + i, "" + i);
        }
    }
}
