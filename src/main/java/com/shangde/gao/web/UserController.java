package com.shangde.gao.web;

import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.domain.User;
import com.shangde.gao.service.UserService;
import com.shangde.gao.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/22
 * Time: 下午12:31
 */
@RestController
@RequestMapping(value = "/api/v1/users")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/{openId}",method = RequestMethod.GET)
    public ResDTO select(@PathVariable String openId){
            User user = new User();
            user.setOpenid(openId);
        return RsJsonManager.getResultJson().reDataSuccess(userService.selectOne(user));
    }

    /**
     * User res dto.
     * 更新用户接口
     * @param openId the open id
     * @param user   the user
     * @return the res dto
     * @author yf -wenhao
     * @date 2017 -10-22 16:22:03
     * @Param openId 小程序id
     */
    @RequestMapping(value = "/{openId}",method = RequestMethod.PUT)
    public ResDTO updateUser(@PathVariable String openId,@RequestBody User user) {
        logger.info(" 跟新用户传入参数为 {}", user.toString());
        user.setOpenid(openId);
        return userService.updateUser(user);

    }

    /**
     * 通过openId查询用户的手机号
     *
     * @param openId the open id
     * @return mobile
     * @author 李钰萍
     */
    @RequestMapping(value = "/{openId}/mobile", method = RequestMethod.GET)
    public ResDTO getMobile(@PathVariable String openId) {
        Map<String, String> map = new HashMap<>();
        String result = userService.getMobileByOpenId(openId);
        map.put("mobile", null == result ? "" : result);
        return RsJsonManager.getResultJson().reDataSuccess(map);
    }

    /**
     * Decrypt res dto.
     * 解密加密信息
     * @param user the user
     * @return the res dto
     * @author yf-wenhao
     * @date 2017 -12-20 17:11:17
     */
    @RequestMapping(value = "/decrypt")
    public ResDTO decrypt(@RequestBody User user){
        logger.info(" 解密传入参数为- encryptedData = {},iv = {},openid = {}", user.getEncryptedData(), user.getIv(), user.getOpenid());
        return userService.decrypt(user);
    }



}
