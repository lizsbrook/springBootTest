package com.yuning.gao.dao.mapper.main;

import com.yuning.gao.domain.BookBean;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

@Mapper
public interface BookMapper extends BaseMapper<BookBean>,ExampleMapper<BookBean> {

}
