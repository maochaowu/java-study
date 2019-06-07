# Redis 使用方式

redis想必大家都不会陌生，redis是一个kv模式的nosql数据库，主要用于缓存处理。redis在使用过程中主要可以使用以下几种模式。分别为单机模式、分片模式、sentinel模式、cluster模式。

## 1.单机使用

单机模式就不用怎么解释了，启动一个单机的redis服务，客户端以单机模式进行连接后，进行数据存储和查询。

## 2.分片使用

分片模式下，服务端还是处于单机模式的部署，仅仅只是客户端连接服务端时采用了分片连接的配置，如下所示，指定了分片连接的配置，JedisShardInfo可以设置相关的权重参数。ShardedJedisPool的逻辑是将list集合中的JedisShardInfo，采用一致性hash算法，将list中每个值，均分为160*权重(默认为1)，然后计算hash后，以hash为key，JedisShardInfo为value存放到TreeMap中。并将JedisShardInfo为true，Jedis为value存放到新建的LinkedHashMap中。当执行set操作时，首先计算key的hash值，然后判断该hash落在TreeMap中的区域，取出最接近的hash值，并获取其value，即JedisShardInfo对象，在通过JedisShardInfo对象到LinkedHashMap中获取jedis连接，然后再用jedis去执行set。get逻辑和set逻辑一致。

优点：相比于单机模式下，通过多机分摊了单机的压力。

缺点：当其中一个分片挂了后，该分片无法再提供服务，要等待该分片恢复正常；另外无法使用redis的事务功能，因为可能事务中的各个操作会落在不同的分片上。

```java
List<JedisShardInfo> list = new ArrayList<>();

list.add(new JedisShardInfo("192.168.200.150", 6379));
list.add(new JedisShardInfo("192.168.200.150", 6380));
list.add(new JedisShardInfo("192.168.200.150", 6381));
//定义redis分片连接池
ShardedJedisPool jedisPool = new ShardedJedisPool(poolconfig, list);
ShardedJedis shardedJedis = jedisPool.getResource();
shardedJedis.set("hello","world");
```

## 3.采用sentinel模式使用

```java
Set<String> sentinelConfig = new HashSet<>();
sentinelConfig.add(new HostAndPort("192.168.200.150", 26379).toString());
sentinelConfig.add(new HostAndPort("192.168.200.137", 26379).toString());
JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster1", sentinelConfig);
Jedis master = sentinelPool.getResource();
master.set("hello","world");
```



## 4.采用cluster模式使用

```java
JedisCluster cluster = new JedisCluster(new HostAndPort("192.168.200.137", 7000));
cluster.set("hello","world");
```

