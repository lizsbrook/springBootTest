package com.shangde.gao.service;

import com.shangde.gao.domain.BookBean;

public interface BookService {
    BookBean selectOne(BookBean bookBean);

    int insert(BookBean bookBean);
}
