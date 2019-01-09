package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.BucketFolder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface BucketFolderMapper extends BaseMapper<BucketFolder> {
    @Select("select id,folder,display_name as displayName  from lite_bucket_folder where bucket = #{bucket} and delete_flag = 0 order by id desc ")
    List<BucketFolder> getFoldersByBucketName(@Param("bucket") String bucket);


}
