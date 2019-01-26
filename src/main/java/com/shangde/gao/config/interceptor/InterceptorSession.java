package com.shangde.gao.config.interceptor;

import com.shangde.gao.domain.LoginUser;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.service.UserService;
import com.shangde.gao.service.redis.RedisService;
import com.shangde.gao.util.JsonUtils;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 登陆安全验证拦截器
 * User: gaoming
 * Date: 17/10/23
 * Time: 下午2:53
 */
public class InterceptorSession implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InterceptorSession.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //get和post请求均会请求两次 第一次是option，对那些可能对服务器数据产生副作用的 HTTP 请求方法
        // （特别是 GET 以外的 HTTP 请求，或者搭配某些 MIME 类型的 POST 请求），
        // 浏览器必须首先使用 OPTIONS 方法发起一个预检请求（preflight request），从而获知服务端是否允许该跨域请求。
        // 服务器确认允许之后，才发起实际的 HTTP 请求
        if(request.getMethod().equals("OPTIONS"))
        {
            return true;
        }
        request.setCharacterEncoding("UTF-8");
        String requestUrl = request.getRequestURI();
        log.debug("当前请求的地址为：{}", requestUrl);

        request.getParameterMap().forEach((s, strings) -> log.info("请求参数名 : {} ： {}", s, Arrays.toString(strings)));


        String token = request.getHeader("X-Token");
        if (StringUtils.isEmpty(token)) {
            setErrorResult(response);
            return false;
        }

        //判断session是否存在
        if (redisService.checkUserOnline(token)) {
            //刷新session时间
            redisService.setRetireTimeForUserName(token);
            LoginUser user = redisService.getSession(token);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("SESSION", token);
            return true;
        }
        setErrorResult(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,  Exception ex) throws Exception {
    }

    //对于有问题的token或者登陆校验失败统一返回401错误
    private void setErrorResult(HttpServletResponse response,String... message) throws IOException {
        ResDTO result = RsJsonManager.getResultJson().getResDTO();
        result.setRs(-1);
        if(message.length <=0){
            result.setResultMessage("LOGOVERTIME");
        }else {
            result.setResultMessage(message[0]);
        }
        response.setStatus(401);
        response.getWriter().write(JsonUtils.toJson(result));
    }

}
