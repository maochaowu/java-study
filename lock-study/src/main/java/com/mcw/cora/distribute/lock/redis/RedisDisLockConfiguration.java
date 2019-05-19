package com.mcw.cora.distribute.lock.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import redis.clients.jedis.JedisShardInfo;

/**
 * @Author yibi
 * Date 2019/5/19 19:54
 * Version 1.0
 **/
@Configuration
@PropertySource(value = "application.properties")
public class RedisDisLockConfiguration {

    private static final String LOCK_KEY = "registryKey";
    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.pwd}")
    private String password;

    @Bean("redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        JedisShardInfo shardInfo = new JedisShardInfo(redisHost);
        shardInfo.setPassword(password);
        RedisConnectionFactory connectionFactory = new JedisConnectionFactory(shardInfo);
        return connectionFactory;
    }

    @Bean("redisLockRegistry")
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory connectionFactory) {
        return new RedisLockRegistry(connectionFactory, LOCK_KEY);
    }
}
