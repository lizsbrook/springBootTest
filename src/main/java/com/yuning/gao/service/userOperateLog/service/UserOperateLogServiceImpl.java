package com.yuning.gao.service.userOperateLog.service;


import com.yuning.gao.dao.mapper.main.UserOperateLogMapper;
import com.yuning.gao.domain.UserOperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/9/19
 * Time: 下午10:14
 */
@Service
public class UserOperateLogServiceImpl implements UserOperateLogService {

    private final UserOperateLogMapper userOperateLogMapper;

    @Autowired
    public UserOperateLogServiceImpl(UserOperateLogMapper userOperateLogMapper) {
        this.userOperateLogMapper = userOperateLogMapper;
    }

    @Override
    public void insert(UserOperateLog userOperateLog) {
        userOperateLogMapper.insertSelective(userOperateLog);
    }
}
