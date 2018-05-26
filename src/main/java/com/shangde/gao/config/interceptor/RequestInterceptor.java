package com.shangde.gao.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 所有的http请求拦截器打印
 * User: gaoming
 * Date: 17/10/23
 * Time: 下午2:53
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        long startTime = (Long) request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;					//执行时间

        String url = request.getRequestURI();				//請求URL
        String queryString = request.getQueryString();		//查询字符串
        String localAddr = request.getLocalAddr();			//本地IP
        int localPort = request.getLocalPort();				//本地Port
        String realIp = request.getHeader("X-Forwarded-For");		//用户真实IP

        logger.info("realIp:{} url:{} queryString:{} localAddr:{} localPort:{}  executeTime:{}",
                realIp, url, queryString, localAddr, localPort,
                executeTime);
        request.getParameterMap().forEach((s, strings) -> logger.info("请求参数名 : {} ： {}", s, Arrays.toString(strings)));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
