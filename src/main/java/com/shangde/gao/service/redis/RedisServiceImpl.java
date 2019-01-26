package com.shangde.gao.service.redis;


import com.shangde.gao.domain.LoginUser;
import com.shangde.gao.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/27
 * Time: 下午12:11
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    // 4层存放用户session 老师信息  默认的站点和接线组
    // 5 存在 redis 锁信息 sort_flag

    private final String templateErrorMessage = "the method {} failed, the reason is : {}";

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static final int logOutTime = 7200;

    //存储admin后台 session
    private int DB_admin_stu = 8;


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

    @Override
    public String addSession(LoginUser user) {
        Jedis jedis = redisDataSource.getRedisClient();
        String sessionId = null;
        if (jedis != null) {
            boolean broken = false;
            try {
                jedis.select(DB_admin_stu);
                sessionId = user.getName() + "_SESSION" + "_" + UUID.randomUUID();
                Set<String> stringSet = jedis.keys(user.getName() + "_SESSION*");
                stringSet.forEach(jedis::del);
                jedis.setex(sessionId, logOutTime, JsonUtils.toJson(user));
            } catch (Exception e) {
                broken = true;
            } finally {
                redisDataSource.returnResource(jedis, broken);
            }
        }
        return sessionId;
    }

    @Override
    public boolean checkUserOnline(String userSession) {
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis != null) {
            boolean broken = false;
            try {
                jedis.select(DB_admin_stu);
                if (jedis.exists(userSession)) {
                    if (!StringUtils.isEmpty( jedis.get(userSession))) {
                        return true;
                    }
                }
            } catch (Exception e) {
                log.error(templateErrorMessage, "checkConsultantOnline", e);
                broken = true;
            } finally {
                redisDataSource.returnResource(jedis, broken);
            }
        }
        return false;
    }

    @Override
    public void setRetireTimeForUserName(String consultantAccount) {
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis != null) {
            boolean broken = false;
            try {
                jedis.select(DB_admin_stu);
                if (jedis.exists(consultantAccount)) {
                    jedis.expire(consultantAccount, logOutTime);
                }
            } catch (Exception e) {
                log.error(templateErrorMessage, "setRetireTimeForUserName", e);
                broken = true;
            } finally {
                redisDataSource.returnResource(jedis, broken);
            }
        }
    }

    @Override
    public LoginUser getSession(String userSession) {
        LoginUser session = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis != null) {
            boolean broken = false;
            try {
                jedis.select(DB_admin_stu);
                if (jedis.exists(userSession)) {
                    String json = jedis.get(userSession);
                    session = JsonUtils.toBean(json, LoginUser.class);
                }
            } catch (Exception e) {
                log.error(templateErrorMessage, "getSession", e);
                broken = true;
            } finally {
                redisDataSource.returnResource(jedis, broken);
            }
        }
        return session;
    }

}
