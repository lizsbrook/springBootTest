package com.shangde.gao.service;

import com.shangde.gao.dao.TransactionalTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JDBCServiceTest {

    @Autowired
    private TransactionalTest jdbcTest;

    public void save()
    {
        jdbcTest.save();
    }


}
