package com.shangde.gao.dao.mapper;

import com.shangde.gao.domain.BookBean;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

@Mapper
public interface BookMapper extends BaseMapper<BookBean>,ExampleMapper<BookBean> {

    //动态SQL 常规化写法
    @InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
    //为了保证插入的record对象中，插入后，注解ID在record中，需要加入@options注解，并且指定key和打开自增长开关
    @Options(useGeneratedKeys = true,keyColumn = "id")
    int insertSelective(BookBean record);

}
