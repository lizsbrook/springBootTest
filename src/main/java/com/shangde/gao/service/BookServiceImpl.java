package com.shangde.gao.service;

import com.shangde.gao.dao.BookMapper;
import com.shangde.gao.domain.BookBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookMapper bookMapper;


    @Override
    public BookBean selectOne(BookBean bookBean) {
        return bookMapper.selectOne(bookBean);
    }

    @Override
    public int insert(BookBean bookBean) {
        return bookMapper.insertSelective(bookBean);
    }
}
