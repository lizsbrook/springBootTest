package com.shangde.gao.dao.manager;


import com.shangde.gao.domain.User;

public interface  UserManager {

    int updateByPrimaryKeySelective(User user);

    User selectOne(User user);

    String getMobileByOpenId(String openId);

    int insert(User user);

    Integer getPrimaryId(String openId, Integer liveCourseId);
}
