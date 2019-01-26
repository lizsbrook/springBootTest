package com.shangde.gao.config.interceptor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 15:15 2018/11/19
 * Description：${description}
 */
//@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
//public class ChannelFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        ServletRequest requestWrapper = null;
//        if(servletRequest instanceof HttpServletRequest) {
//            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
//        }
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
////        response.setHeader("Access-Control-Allow-Origin", "*");
////        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD"); // or *
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, X-Token"); // or *
//        if(requestWrapper == null) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            filterChain.doFilter(requestWrapper, servletResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
