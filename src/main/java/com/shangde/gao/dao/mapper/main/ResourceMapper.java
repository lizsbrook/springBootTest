package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("select a.* from lite_resource a,lite_bucket_folder_resource b where b.bucket_folder_id = #{bucketFolderId} and a.id = b.resource_id and a.delete_flag = 0 ")
    List<Resource> getResourcesByFolderId(@Param("bucketFolderId") Integer bucketFolderId);
}
