package com.shangde.gao.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JDBCTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save()
    {
        System.out.println("save start");
        jdbcTemplate.update("INSERT  into testgm(name) VALUE(?) ","test1");
        jdbcTemplate.update("INSERT  into testgm(name) VALUE(?) ","test2");
        System.out.println("save end");
    }

}
