package com.yuning.gao.web;

import com.yuning.gao.config.dependConfig.WXConfig;
import com.yuning.gao.domain.ResDTO;
import com.yuning.gao.domain.RsJsonManager;
import com.yuning.gao.domain.User;
import com.yuning.gao.domain.UserOperateLog;
import com.yuning.gao.service.UserService;
import com.yuning.gao.service.redis.RedisService;
import com.yuning.gao.service.userOperateLog.service.UserOperateLogService;
import com.yuning.gao.util.HttpClientUtils;
import com.yuning.gao.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
   小程序交互接口
 */
@RestController
@RequestMapping(value = "/api/v1/lite")
public class LiteProcessController {

    private static final Logger logger = LoggerFactory.getLogger(LiteProcessController.class);
    private final WXConfig wxConfig;
    private final RedisService redisService;
    private final UserService userService;
    private final UserOperateLogService userOperateLogService;

    @Autowired
    public LiteProcessController(UserOperateLogService userOperateLogService, UserService userService, RedisService redisService, WXConfig wxConfig) {
        this.userOperateLogService = userOperateLogService;
        this.userService = userService;
        this.redisService = redisService;
        this.wxConfig = wxConfig;
    }

    @RequestMapping(value = "/session/{code}", method = RequestMethod.GET)
    public ResDTO getSession(@PathVariable String code) {
        String url = wxConfig.getLoginUrL() + "?appid=" + wxConfig.getAppid() + "&secret=" + wxConfig.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        logger.info("登录请求参数" + url);
        String result = HttpClientUtils.getInstance().doPost(url);
        logger.info("登录请求返回结果" + result);
        Map map = JsonUtils.toBean(result, Map.class);
        String openid = (String) map.get("openid");
        if (StringUtils.isEmpty(openid)) {
            return RsJsonManager.getResultJson().reError("获取不到用户的信息");
        }
        User user = userService.insert(openid);
        //设置session key
        redisService.setSessionKey((String) map.get("openid"), (String) map.get("session_key"));
        map.remove("session_key");
        map.put("appid", "wx50da7efdcdf03f28");
        map.put("isNickNameAuth", 0);
        if (!StringUtils.isEmpty(user.getNickname())) {
            map.put("isNickNameAuth", 1);
            map.put("avatarUrl", user.getAvatarUrl());
            map.put("nickName", user.getNickname());
        }
        map.put("isMobileAuth", 0);
        if (!StringUtils.isEmpty(user.getMobile())) {
            map.put("isMobileAuth", 1);
            map.put("mobile", user.getMobile());
        }
        //记录用户登录日志
        userOperateLogService.insert(new UserOperateLog("login", openid, null));
        return RsJsonManager.getResultJson().reDataSuccess(map, "成功");
    }

}
