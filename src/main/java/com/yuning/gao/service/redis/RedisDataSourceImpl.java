package com.yuning.gao.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisDataSourceImpl implements RedisDataSource {

    private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    public Jedis getRedisClient() {
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            log.error("getRedisClent error", e);
        }
        return null;
    }

    public void returnResource(Jedis jedis) {
    	jedisPool.returnResource(jedis);
    }

    public void returnResource(Jedis jedis, boolean broken) {
        if (broken) {
        	jedisPool.returnBrokenResource(jedis);
        } else {
        	jedisPool.returnResource(jedis);
        }
    }
}
