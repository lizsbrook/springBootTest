package com.shangde.gao.config.mvc;

import com.shangde.gao.config.interceptor.InterceptorSession;
import com.shangde.gao.config.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
        registry.addInterceptor(interceptorSession()).excludePathPatterns("/api/v1/admin/user/login/**")
                .addPathPatterns("/api/**");
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }

    //参数解析器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        //暂时不添加方法参数解析
        //argumentResolvers.add(new SessionArgumentResolver());
    }

    //跨域设置
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}
