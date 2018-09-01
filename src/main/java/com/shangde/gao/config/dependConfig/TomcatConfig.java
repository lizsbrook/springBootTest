package com.shangde.gao.config.dependConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 16:26 2018/7/24
 * Description：${description}
 */
@Configuration
@ConfigurationProperties(prefix = "spring.server")
@Data
public class TomcatConfig {
    private String MaxFileSize;
    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(MaxFileSize); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(MaxRequestSize);
        return factory.createMultipartConfig();
    }
}
