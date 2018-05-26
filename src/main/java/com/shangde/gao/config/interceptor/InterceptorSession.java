package com.shangde.gao.config.interceptor;

import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.service.UserService;
import com.shangde.gao.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆安全验证拦截器
 * User: gaoming
 * Date: 17/10/23
 * Time: 下午2:53
 */
public class InterceptorSession implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorSession.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("InterceptorSession请求URL :" + request.getRequestURI());
        //暂时不做token和登陆用户校验
        //判断token
//        String token = request.getHeader("token");
//        //LOGOVERTIME 注销未登录状态
//        if (null == token) {
//            setErrorResult(response);
//            return false;
//        }
//        logger.info("请求URL :" + request.getRequestURI() + "当前登录请求token : {}", token);
//        //token无效统一也返回401错误
//        Claims claims = JwtUtil.parseJWT(token);
//        if(null == claims)
//        {
//            setErrorResult(response, "token无效,解析的claims为空");
//            return false;
//        }
//        claims.getSubject();
//        //获取私秘信息 判断用户是否存在
//        User user = userService.selectByPrimaryKey(Integer.valueOf(claims.getSubject()));
//        if (null == user) {
//            setErrorResult(response, "用户不存在");
//            return false;
//        }
//        request.setAttribute("user", user);
        return true;
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
