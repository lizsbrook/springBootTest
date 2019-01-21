package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

import javax.annotation.Resources;
import java.util.List;

@Mapper
@Repository
public interface ResourceMapper extends BaseMapper<Resource>,MySqlMapper<Resource>{

    @InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
    @Options(useGeneratedKeys = true)
    int insertSelective(Resource record);

    @Select("select a.id as id,a.url as url,a.type as type, a.poster_url as posterUrl,a.short_description as shortDescription,\n" +
            "a.long_description as longDescription,a.content_type as contentType,c.display_name as bucketFolderName,a.generate_time as generateTime,FROM_UNIXTIME(UNIX_TIMESTAMP(a.generate_time), '%Y-%m-%d') as generateTimeStr \n" +
            "from lite_resource a,lite_bucket_folder_resource b ,lite_bucket_folder c\n" +
            "where b.bucket_folder_id = #{bucketFolderId} and a.id = b.resource_id and a.delete_flag = 0 and c.delete_flag = 0 and c.id = b.bucket_folder_id\n" +
            "order by a.id desc limit #{startNum},#{counts}")
    List<Resource> getResourcesByBucketFolderId(@Param("bucketFolderId") Integer bucketFolderId,@Param("startNum") Integer startNum,@Param("counts") Integer counts);

    @Select("select count(a.id) from lite_resource a,lite_bucket_folder_resource b ,lite_bucket_folder c\n" +
            "where b.bucket_folder_id = #{bucketFolderId} and a.id = b.resource_id and a.delete_flag = 0 and c.delete_flag = 0 and c.id = b.bucket_folder_id\n" +
            "order by a.id desc")
    Integer getResourcesByBucketFolderIdTotalNum(@Param("bucketFolderId") Integer bucketFolderId);

    @Select("select c.* from lite_bucket_folder a,lite_bucket_folder_resource b,lite_resource c\n" +
            "where a.bucket = #{bucketName} and a.folder=#{folderName} and a.id= b.bucket_folder_id and b.resource_id = c.id")
    List<Resource> getResourcesByBucketAndFolderName(@Param("bucketName") String bucketName,@Param("folderName") String folderName);


    @Select("select a.id as id,a.url as url,a.type as type, a.poster_url as posterUrl,a.short_description as shortDescription,\n" +
            "a.long_description as longDescription,a.content_type as contentType,c.display_name as bucketFolderName,c.id as bucketFolderId,a.generate_time as generateTime,FROM_UNIXTIME(UNIX_TIMESTAMP(a.generate_time), '%Y-%m-%d') as generateTimeStr\n" +
            "from lite_resource a,lite_bucket_folder_resource b ,lite_bucket_folder c\n" +
            "where a.id = b.resource_id and a.delete_flag = 0 and c.delete_flag = 0 and c.id = b.bucket_folder_id and c.bucket = #{bucket}\n" +
            "order by a.id desc limit #{startNum},#{counts}")
    List<Resource> getResourcesByBucket(@Param("bucket") String bucket,@Param("startNum") Integer startNum,@Param("counts") Integer counts);

    @Select("select count(a.id) from lite_resource a,lite_bucket_folder_resource b ,lite_bucket_folder c\n" +
            "where  a.id = b.resource_id and a.delete_flag = 0 and c.delete_flag = 0 and c.id = b.bucket_folder_id and c.bucket = #{bucket}\n" +
            "order by a.id desc")
    Integer getResourcesByBucketTotalNum(@Param("bucket") String bucket);
}
