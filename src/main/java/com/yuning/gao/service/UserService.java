package com.yuning.gao.service;

import com.yuning.gao.domain.LoginUser;
import com.yuning.gao.domain.ResDTO;
import com.yuning.gao.domain.User;

public interface UserService {
    String getMobileByOpenId(String unionid);

    ResDTO updateUser(User user);

    User selectOne(User user);

    User insert(String openid);

    ResDTO decrypt(User user);

    LoginUser findUserByName(String name,String password);
}