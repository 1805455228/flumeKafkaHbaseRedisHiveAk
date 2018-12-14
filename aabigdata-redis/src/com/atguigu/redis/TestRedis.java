package com.atguigu.redis;

import redis.clients.jedis.Jedis;

/**
 * @author zxfcode
 * @create 2018-11-02 10:15
 */
public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop104",6379);
        String pong = jedis.ping();
        System.out.println(pong);
    }
}
