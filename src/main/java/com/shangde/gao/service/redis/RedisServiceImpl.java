package com.shangde.gao.service.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/27
 * Time: 下午12:11
 */
@Service
public class RedisServiceImpl implements RedisService {

    // 4层存放用户session 老师信息  默认的站点和接线组
    // 5 存在 redis 锁信息 sort_flag

    private final String templateErrorMessage = "the method {} failed, the reason is : {}";

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisDataSource redisDataSource;

    @Override
    public String getSessionKey(String key) {
        Jedis jedis = redisDataSource.getRedisClient();
        String sessionKey = null;
        if (jedis != null) {
            boolean broken = false;
            try {
                jedis.select(4);
                if (jedis.exists(key)) {
                    sessionKey = jedis.get(key);
                }
            } catch (Exception e) {
                logger.error(templateErrorMessage, "getSessionKey", e);
                broken = true;
            } finally {
                redisDataSource.returnResource(jedis, broken);
            }
        }
        return sessionKey;
    }

    @Override
    public void setSessionKey(String key, String session_key) {
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis != null) {
            boolean broken = false;
            try {
                jedis.select(4);
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                jedis.set(key, session_key);
            } catch (Exception e) {
                logger.error(templateErrorMessage, "getSessionKey", e);
                broken = true;
            } finally {
                redisDataSource.returnResource(jedis, broken);
            }
        }
    }
}
