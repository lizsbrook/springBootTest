package com.shangde.gao.dao.mapper.main;

import com.shangde.gao.domain.BucketFolderResource;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
@Repository
public interface BucketFolderResourceMapper extends BaseMapper<BucketFolderResource> {
}
