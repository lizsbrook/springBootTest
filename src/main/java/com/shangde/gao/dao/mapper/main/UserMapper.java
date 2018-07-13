package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;


/**
* 通用 mapper 代码生成器
*
* @author mapper-generator
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT mobile FROM lite_user WHERE openid = #{openId}")
    String getMobileByOpenId(String openId);

    @Select("SELECT id FROM lite_user_sign_up WHERE open_id = #{openId} AND live_course_id=#{liveCourseId}")
    Integer getPrimaryId(@Param("openId") String openId, @Param("liveCourseId") Integer liveCourseId);
}




