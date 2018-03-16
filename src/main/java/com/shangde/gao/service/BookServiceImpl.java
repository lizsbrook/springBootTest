package com.shangde.gao.service;

import com.shangde.gao.dao.BookMapper;
import com.shangde.gao.domain.BookBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {

    private final  static Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;


    @Override
    public BookBean selectOne(BookBean bookBean) {
        return bookMapper.selectOne(bookBean);
    }

    @Override
    public int insert(BookBean bookBean) {
        log.info("BookServiceImpl:start insert one bookbean!");
        int resultId = bookMapper.insertSelective(bookBean);
        log.info("BookServiceImpl:resultId {},BookBean = {}",resultId,bookBean);
        return resultId;
    }
}
