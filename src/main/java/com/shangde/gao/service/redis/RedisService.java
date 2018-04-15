package com.shangde.gao.service.redis;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/27
 * Time: 下午12:11
 */
public interface RedisService {
    //获取 解密 秘钥
    String getSessionKey(String oppenId);
    //设置 解密 密钥
    void setSessionKey(String oppenId, String session_key);

}
