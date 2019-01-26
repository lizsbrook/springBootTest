package com.shangde.gao.service;

import com.shangde.gao.domain.LoginUser;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.User;

public interface UserService {
    String getMobileByOpenId(String unionid);

    ResDTO updateUser(User user);

    User selectOne(User user);

    User insert(String openid);

    ResDTO decrypt(User user);

    LoginUser findUserByName(String name,String password);
}