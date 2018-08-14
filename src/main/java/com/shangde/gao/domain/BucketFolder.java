package com.shangde.gao.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "`lite_bucket_folder`")
public class BucketFolder extends MetaEntity {
    @ApiModelProperty(value = "阿里云oss bucket名称")
    @Column(name = "`bucket`")
    private String bucket;

    @ApiModelProperty(value = "阿里云oss文件夹名称")
    @Column(name = "`folder`")
    private Integer folder;

    @ApiModelProperty(value = "小程序/电脑端显示的类别名称")
    @Column(name = "`display_name`")
    private String displayName;

}