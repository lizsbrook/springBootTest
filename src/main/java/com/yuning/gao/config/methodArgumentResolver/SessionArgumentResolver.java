package com.yuning.gao.config.methodArgumentResolver;

import com.yuning.gao.config.annotation.GetSession;
import com.yuning.gao.domain.LoginUser;
import com.yuning.gao.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * The get Session argument resolver.
 */
public class SessionArgumentResolver implements HandlerMethodArgumentResolver {


    private static Logger logger = LoggerFactory.getLogger(SessionArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterAnnotation(GetSession.class) != null && parameter.getParameterType() == LoginUser.class) {
            //支持解析该参数
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request= (HttpServletRequest) webRequest.getNativeRequest();
        //这里暂时把User对象放在session中
        logger.info(" 当前登录人的session 为 "+ request.getSession());
        LoginUser userSession = (LoginUser) request.getSession().getAttribute("user");
        logger.info(" 当前登录人的session 为 "+ userSession);
        return userSession;
    }
}