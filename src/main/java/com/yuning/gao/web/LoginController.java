package com.yuning.gao.web;


import com.yuning.gao.domain.LoginUser;
import com.yuning.gao.service.UserService;
import com.yuning.gao.service.redis.RedisService;
import com.yuning.gao.util.JsonUtils;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/2/22
 * Time: 下午9:21
 * 用户登录退出接口
 */
@RestController
@RequestMapping(value = "/api/v1/admin/user")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    /**
     * Login string
     * 用户登录请求
     *
     * @param loginUser the loginUser
     * @return the string
     * @author yf-wenhao
     * @date 2018 -02-23 16:36:28
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody LoginUser loginUser, HttpServletResponse response) {
        LoginUser user = userService.findUserByName(loginUser.getName(),loginUser.getPassword());
        String sessionId = redisService.addSession(user);
        Map<String, Object> testUser = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        testUser.put("roles", roles);
        String token = "admin";
        try {
            token = URLEncoder.encode(sessionId, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        testUser.put("token", token);
        testUser.put("introduction", "我是超级管理员");
        testUser.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        testUser.put("name", "Super Admin");
        return JsonUtils.toJson(testUser);
    }

    /**
     * create by: gaoming01
     * description:管理后台获取用户详细信息
     * create time: 18:22 2019/1/25
     *
     * @Param: null
     * @return
     */
    @GetMapping(value = "/infos")
    public String infos() {
        Map<String, Object> testUser = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        testUser.put("roles", roles);
        testUser.put("token", "admin");
        testUser.put("introduction", "我是超级管理员");
        testUser.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        testUser.put("name", "Super Admin");
        return JsonUtils.toJson(testUser);
    }


}
