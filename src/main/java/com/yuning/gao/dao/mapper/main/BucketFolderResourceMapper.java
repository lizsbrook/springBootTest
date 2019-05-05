package com.yuning.gao.dao.mapper.main;

import com.yuning.gao.domain.BucketFolderResource;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
@Repository
public interface BucketFolderResourceMapper extends BaseMapper<BucketFolderResource> {
}
