package com.shangde.gao.service;

import com.shangde.gao.dao.BookMapper;
import com.shangde.gao.domain.BookBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookMapper bookMapper;

    @Override
    public void insertOneBook(BookBean bookBean) {
        bookMapper.insert(bookBean);
    }
}
