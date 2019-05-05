package com.yuning.gao.config.dependConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 21:25 2019/2/22
 * Description：${description}
 */
@Configuration
@ConfigurationProperties(prefix = "wisdomdevice")
@Data
public class WisdomdeviceConfig {
    private Integer servicePort;
}
