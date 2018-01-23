package com.shangde.gao.dao;

import com.shangde.gao.domain.BookBean;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

@Mapper
public interface BookMapper extends BaseMapper<BookBean>,ExampleMapper<BookBean> {

}
