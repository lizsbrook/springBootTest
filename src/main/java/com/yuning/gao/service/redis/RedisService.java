package com.yuning.gao.service.redis;

import com.yuning.gao.domain.LoginUser; /**
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

    String addSession(LoginUser user);

    boolean checkUserOnline(String decode);

    void setRetireTimeForUserName(String decode);

    LoginUser getSession(String decode);
}
