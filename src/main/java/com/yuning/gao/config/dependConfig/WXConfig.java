package com.yuning.gao.config.dependConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 11:15 2018/7/13
 * Description：${description}
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WXConfig {
    private String appid;

    private String secret;

    private String loginUrL;
}
