package com.yuning.gao.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //事务注解，表明事务是方法级的。方法如果没有加注解，默认是非事务级别的
    @Transactional
    public void save()
    {
        System.out.println("save start");
        jdbcTemplate.update("INSERT  into testgm(name) VALUE(?) ","test1");
        System.out.println(1/0);
        jdbcTemplate.update("INSERT  into testgm(name) VALUE(?) ","test2");
        System.out.println("save end");
    }

}
