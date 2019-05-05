package com.yuning.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "`lite_bucket_folder`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BucketFolder extends MetaEntity {
    public BucketFolder()
    {
        super(0);
    }

    @ApiModelProperty(value = "阿里云oss bucket名称")
    @Column(name = "`bucket`")
    private String bucket;

    @ApiModelProperty(value = "阿里云oss文件夹名称")
    @Column(name = "`folder`")
    private String folder;

    @ApiModelProperty(value = "小程序/电脑端显示的类别名称")
    @Column(name = "`display_name`")
    private String displayName;

}
