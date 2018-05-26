package com.shangde.gao.config;

import com.shangde.gao.config.interceptor.InterceptorSession;
import com.shangde.gao.config.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public InterceptorSession interceptorSession() {
        return new InterceptorSession();
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
//        registry.addInterceptor(interceptorSession()).excludePathPatterns("/api/v1/login/**")
//                .addPathPatterns("/api/**");

        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }

    //参数解析器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        //暂时不添加方法参数解析
        //argumentResolvers.add(new SessionArgumentResolver());
    }
}
