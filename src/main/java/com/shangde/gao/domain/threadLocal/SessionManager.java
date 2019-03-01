package com.shangde.gao.domain.threadLocal;

import com.shangde.gao.domain.LoginUser;
import com.shangde.gao.domain.exception.UnauthorizedException;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/7/16
 * Time: 下午6:47
 */
public class SessionManager {

    private static final ThreadLocal<LoginUser> userThreadLocal = new ThreadLocal<>();


    public static void setSession(LoginUser user) {
        userThreadLocal.set(user);
    }

    public static LoginUser getSession() {
        LoginUser user = userThreadLocal.get();
        if (null == user || StringUtils.isEmpty(user.getName())) {
            throw new UnauthorizedException("用户session为null");
        }
        return userThreadLocal.get();
    }

    public static  void delSession() {
        userThreadLocal.remove();
    }

}
