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

sentinel模式下，客户连接池连接的是sentinel的端口，sentinel会自动获取到集群中的master节点，供客户端进行连接，sentinel顾名思义即哨兵，redis-server在启动后，都会注册到sentinel中，sentinel集群负责管理redis的集群，主要用于redis集群的master和salve的选举。（有可能出现脑裂的情况）。

优点：采用sentinel集群模式可以方便管理redis集群，当redis集群master节点挂掉后，可以通过投票从slave中选举出新的节点做为master节点。

缺点：可能出现脑裂的情况，导致数据丢失。

```java
Set<String> sentinelConfig = new HashSet<>();
sentinelConfig.add(new HostAndPort("192.168.200.150", 26379).toString());
sentinelConfig.add(new HostAndPort("192.168.200.137", 26379).toString());
JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster1", sentinelConfig);
Jedis master = sentinelPool.getResource();
master.set("hello","world");
```

## 4.采用cluster模式使用

采用cluster模式进行部署时，是完全去中心化的部署方式，所有的redis节点通过点对点的方式进行通信，集群中的节点存储在nodes.conf文件中。在集群部署模式下，redis集群将整个集群分成了16383个slot。如下为6个节点的集群，三主三从（可以看到第一个主节点占据的是0-5460的slot、第二个主节点占据的是5461-10922的slot、第三个主节点占据的是10923-16383），当进行写入和查找时，通过key的hash对16383取模，即可获得数据需要存储在那个slot中，进而知道在那个节点中进行处理。

b94cb16571560c023910daad7e0ac9d6b85dfd20 192.168.200.137:7000@17000 master - 0 1559737840539 1 connected 0-5460
57952c92477771eec02d803fe8f0f75aad566d9e 192.168.200.137:7002@17002 master - 0 1559737840000 3 connected 10923-16383
c1428c60af3b7bc912919498ec6ad0e92daa1077 192.168.200.137:7005@17005 slave 57952c92477771eec02d803fe8f0f75aad566d9e 0 1559737839000 6 connected
4c2b0c8520e0e113fb97d03354a08fe960190c08 192.168.200.137:7004@17004 slave f08d3fc6b8177fa78f18972ba538b829b9945fc5 0 1559737841000 5 connected
813f327ef687084d672d37753f5c7b99f46b6b95 192.168.200.137:7003@17003 slave b94cb16571560c023910daad7e0ac9d6b85dfd20 0 1559737841545 4 connected
f08d3fc6b8177fa78f18972ba538b829b9945fc5 192.168.200.137:7001@17001 myself,master - 0 1559737838000 2 connected 5461-10922

```java
JedisCluster cluster = new JedisCluster(new HostAndPort("192.168.200.137", 7000));
cluster.set("hello","world");
```

