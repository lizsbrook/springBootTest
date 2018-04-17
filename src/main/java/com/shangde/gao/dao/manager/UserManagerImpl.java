package com.shangde.gao.dao.manager;

import com.shangde.gao.dao.mapper.UserMapper;
import com.shangde.gao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/22
 * Time: 下午4:23
 */
@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectOne(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public String getMobileByOpenId(String openId){
        return userMapper.getMobileByOpenId(openId);
    }

    @Override
    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer getPrimaryId(String openId, Integer liveCourseId) {
        return userMapper.getPrimaryId(openId, liveCourseId);
    }

}
