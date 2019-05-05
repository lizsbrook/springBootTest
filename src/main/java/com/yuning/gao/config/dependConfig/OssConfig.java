package com.yuning.gao.config.dependConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 11:27 2018/8/22
 * Description：${description}
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ossconfig")
public class OssConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
}
