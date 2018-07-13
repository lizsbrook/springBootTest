package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.BookBean;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

@Mapper
public interface BookMapper extends BaseMapper<BookBean>,ExampleMapper<BookBean> {

}
