package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.BucketFolder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface BucketFolderMapper extends BaseMapper<BucketFolder> {
    @Select("select * from lite_bucket_folder where bucket = #{bucket} and delete_flag = 0 order by id desc ")
    List<BucketFolder> getFoldersByBucketName(@Param("bucket") String bucket);
}
