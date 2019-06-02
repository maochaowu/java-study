package com.mcw.cora.redis.capacity;

import redis.clients.jedis.Jedis;

/**
 * @Author yibi
 * Date 2019/6/2 13:40
 * Version 1.0
 **/
public class CapacityTest {

    public static Jedis jedis = new Jedis("192.168.200.150", 6379);

    public static void main(String[] args) {
        long usedMemory1 = getMemoryInfo();
        System.out.println(usedMemory1);

        insertData();
        long usedMemory2 = getMemoryInfo();
        System.out.println(usedMemory2 - usedMemory1);
    }

    private static void insertData() {
        for (int i = 10000; i < 100000; i++) {
            jedis.set("aa" + i, "aa" + i);
        }
    }

    private static long getMemoryInfo() {
        String memoryInfo = jedis.info("memory");
        String usedMemory = memoryInfo.split("\r\n")[1];
        return Long.valueOf(usedMemory.split(":")[1]);
    }
}
