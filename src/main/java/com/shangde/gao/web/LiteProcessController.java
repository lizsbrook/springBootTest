package com.shangde.gao.web;

import com.shangde.gao.config.dependConfig.WXConfig;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.domain.User;
import com.shangde.gao.domain.UserOperateLog;
import com.shangde.gao.service.UserService;
import com.shangde.gao.service.redis.RedisService;
import com.shangde.gao.service.userOperateLog.service.UserOperateLogService;
import com.shangde.gao.util.HttpClientUtils;
import com.shangde.gao.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
   小程序交互接口
 */
@RestController
@RequestMapping(value = "/lite")
public class LiteProcessController {


    @Autowired
    private WXConfig wxConfig;

    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public String userLogin(String code,HttpServletResponse response)
    {
        String url = wxConfig.getLoginUrL() + "?appid=" + wxConfig.getAppid() + "&secret=" + wxConfig.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        System.out.println("登录请求参数" + url);
        String result = HttpClientUtils.getInstance().doPost(url);
        System.out.println("登录请求返回结果"+result);

        Map map = JsonUtils.toBean(result, Map.class);
        String openid = (String) map.get("openid");
        if (StringUtils.isEmpty(openid)) {
            Map returnjSON = new HashMap();
            returnjSON.put("Status","Fairule!");
            return "no valid user info!!!";
        }
        //注册用户
        //userService.insert(new User(openid, (String) map.get("unionid")));
        //设置session key
        //redisService.setSessionKey((String) map.get("openid"), (String) map.get("session_key"));
        //map.remove("session_key");
        map.put("appid", wxConfig.getAppid());
        map.put("secret",wxConfig.getSecret());
        String clientData = JsonUtils.toJson(map);
        Map returnjSON = new HashMap();
        returnjSON.put("Status","SUCCESS");
        returnjSON.put("dataResult",clientData);
        return JsonUtils.toJson(returnjSON);
    }


    @RequestMapping("/doTest")
    public String doTest(HttpServletRequest request, HttpServletResponse response)
    {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        //获取微信小程序get的参数值并打印
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map param = new HashMap();
        System.out.println("username="+username+" ,password="+password);
        param.put("userId",123);
        param.put("password",456);
        return JsonUtils.toJson(param);
    }

    @RestController
    @RequestMapping("/api/v1")
    public static class LoginController {

        private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

        private final WXConfig wxConfig;
        private final RedisService redisService;
        private final UserService userService;
        private final UserOperateLogService userOperateLogService;

        @Autowired
        public LoginController(UserOperateLogService userOperateLogService, UserService userService, RedisService redisService, WXConfig wxConfig) {
            this.userOperateLogService = userOperateLogService;
            this.userService = userService;
            this.redisService = redisService;
            this.wxConfig = wxConfig;
        }

        @RequestMapping(value = "/session/{code}",method = RequestMethod.GET)
        public ResDTO getSession(@PathVariable String code){
            String url = wxConfig.getLoginUrL() + "?appid=" + wxConfig.getAppid() + "&secret=" + wxConfig.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
            logger.info("登录请求参数" + url);
            String result = HttpClientUtils.getInstance().doPost(url);
            logger.info("登录请求返回结果"+result);
            Map map = JsonUtils.toBean(result, Map.class);
            String openid = (String) map.get("openid");
            if (StringUtils.isEmpty(openid)) {
                return RsJsonManager.getResultJson().reError("获取不到用户的信息");
            }
            User user = userService.insert(openid);
            //设置session key
            redisService.setSessionKey((String) map.get("openid"), (String) map.get("session_key"));
            map.remove("session_key");
            map.put("appid","wx50da7efdcdf03f28");
            map.put("isNickNameAuth",0);
            if(!StringUtils.isEmpty(user.getNickname()))
            {
                map.put("isNickNameAuth",1);
                map.put("avatarUrl",user.getAvatarUrl());
                map.put("nickName",user.getNickname());
            }
            map.put("isMobileAuth",0);
            if(!StringUtils.isEmpty(user.getMobile()))
            {
                map.put("isMobileAuth",1);
                map.put("mobile",user.getMobile());
            }
            //记录用户登录日志
            userOperateLogService.insert(new UserOperateLog("login", openid, null));
            return RsJsonManager.getResultJson().reDataSuccess(map, "成功");
        }

    }
}
