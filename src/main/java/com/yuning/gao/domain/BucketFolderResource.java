package com.yuning.gao.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "`lite_bucket_folder_resource`")
public class BucketFolderResource extends MetaEntity {
    @ApiModelProperty(value = "阿里云oss bucket folder Id")
    @Column(name = "`bucket_folder_id`")
    private Integer bucketFolderId;

    @ApiModelProperty(value = "资源ID")
    @Column(name = "`resource_id`")
    private Integer resourceId;

    public BucketFolderResource(Integer bucketFolderId, Integer resourceId) {
        super(0);
        this.bucketFolderId = bucketFolderId;
        this.resourceId = resourceId;
    }
}
