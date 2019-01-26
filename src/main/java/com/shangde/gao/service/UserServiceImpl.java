package com.shangde.gao.service;

import com.shangde.gao.dao.manager.UserManager;
import com.shangde.gao.dao.mapper.admin.SysUserMapper;
import com.shangde.gao.domain.LoginUser;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.domain.User;
import com.shangde.gao.domain.exception.NotFoundException;
import com.shangde.gao.domain.exception.UnauthorizedException;
import com.shangde.gao.service.redis.RedisService;
import com.shangde.gao.util.JsonUtils;
import com.shangde.gao.util.WXDecrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shangde.gao.domain.RsJsonManager.successDate;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserManager userManager;

    private final RedisService redisService;

    private final SysUserMapper sysUserMapper;

    public UserServiceImpl(UserManager userManager, RedisService redisService, SysUserMapper sysUserMapper) {
        this.userManager = userManager;
        this.redisService = redisService;
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public ResDTO updateUser(User user) {
        //通过条件查询是否存在
        User paramUser = new User(user.getOpenid());
        User selectUser = userManager.selectOne(paramUser);
        if (null == selectUser) {
            return RsJsonManager.getResultJson().reError("找不到此用户");
        }
        //返回给前端的手机号
        Map<String, String> map = new HashMap<>();
        map.put("mobile", selectUser.getMobile());

        //设置主键
        user.setId(selectUser.getId());
        if (null != user.getEncryptedData() && null != user.getIv()) {
            //解密
            String sessionKey = redisService.getSessionKey(user.getOpenid());
            if (null == sessionKey) {
                return RsJsonManager.getResultJson().reError("sessionKey 失效，请重新登陆");
            }
            String mobile = WXDecrypt.getMeta("purePhoneNumber", user.getEncryptedData(), sessionKey, user.getIv());
            logger.info("mobile :{}", mobile);
            if (null == mobile) {
                return RsJsonManager.getResultJson().reError("解密出现错误");
            }
            if (StringUtils.isEmpty(selectUser.getMobile()) || !selectUser.getMobile().equals(mobile)) {
                user.setMobile(mobile);
                map.put("mobile", user.getMobile());
            }
        }
        userManager.updateByPrimaryKeySelective(user);

        return successDate(map);
    }


    @Override
    public User selectOne(User user) {
        return userManager.selectOne(user);
    }

    @Override
    public User insert(String openid) {
        User userParam = new User(openid);
        User userSelect = userManager.selectOne(userParam);
        if (null == userSelect) {
            userManager.insert(userParam);
            return userParam;
        }
        return userSelect;

    }


    @Override
    public ResDTO decrypt(User user) {
        String sessionKey = redisService.getSessionKey(user.getOpenid());
        if (null == sessionKey) {
            return RsJsonManager.getResultJson().reError("sessionKey 失效，请重新登陆");
        }
        String result = WXDecrypt.decrypt(user.getEncryptedData(), sessionKey, user.getIv());
        logger.info("decrypt over ,user info :{}", result);
        return successDate(JsonUtils.toBean(result, Map.class));
    }

    @Override
    public LoginUser findUserByName(String name,String password) {
        List<LoginUser> userList = sysUserMapper.select(new LoginUser(name,password));
        if(CollectionUtils.isEmpty(userList))
        {
            throw  new UnauthorizedException("用户名找不到或者密码错误");
        }
        return userList.get(0);
    }


    @Override
    public String getMobileByOpenId(String openId) {
        return userManager.getMobileByOpenId(openId);
    }

}