package com.shangde.gao.web;

import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.domain.User;
import com.shangde.gao.service.UserService;
import com.shangde.gao.service.redis.RedisService;
import com.shangde.gao.util.HttpClientUtils;
import com.shangde.gao.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Value("${wxLogin.url}")
    private String wxLoginUrl;
    @Value("${appid}")
    private String appid;
    @Value("${secret}")
    private String secret;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/session/{code}",method = RequestMethod.GET)
    public ResDTO getSession(@PathVariable String code){
        String url = wxLoginUrl + "?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        logger.info("登录请求参数" + url);
        String result = HttpClientUtils.getInstance().doPost(url);
        logger.info("登录请求返回结果"+result);
        Map map = JsonUtils.toBean(result, Map.class);
        String openid = (String) map.get("openid");
        if (StringUtils.isEmpty(openid)) {
            return RsJsonManager.getResultJson().reError("获取不到用户的信息");
        }
        userService.insert(new User(openid));
        //设置session key
        redisService.setSessionKey((String) map.get("openid"), (String) map.get("session_key"));
        map.remove("session_key");
        return RsJsonManager.getResultJson().reDataSuccess(map, "成功");
    }

}
