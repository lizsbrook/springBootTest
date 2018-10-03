package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.UserOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 15:18 2018/10/3
 * Description：${description}
 */
@Mapper
@Repository
public interface UserOperateLogMapper extends BaseMapper<UserOperateLog> {
}

