package com.shangde.gao.service;

import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.User;

public interface UserService {
    String getMobileByOpenId(String unionid);

    ResDTO updateUser(User user);

    User selectOne(User user);

    int insert(User user);

    ResDTO decrypt(User user);
}