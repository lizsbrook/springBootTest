package com.yuning.gao.dao.mapper.admin;

import com.yuning.gao.domain.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 11:38 2018/7/13
 * Description：${description}
 */
@Mapper
public interface SysUserMapper extends BaseMapper<LoginUser>{
}
