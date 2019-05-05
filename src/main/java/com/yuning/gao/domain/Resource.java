package com.yuning.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Table(name = "`lite_resource`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource extends MetaEntity {

    @ApiModelProperty(value = "资源URL")
    @Column(name = "`url`")
    private String url;

    @ApiModelProperty(value = "视频资源封面图片URL")
    @Column(name = "`poster_url`")
    private String posterUrl;

    @ApiModelProperty(value = "资源类型  0:图片 1:视频")
    @Column(name = "`type`")
    private Integer type;

    @ApiModelProperty(value = "资源名称")
    @Column(name = "`name`")
    private String name;

    @ApiModelProperty(value = "资源简述")
    @Column(name = "`short_description`")
    private String shortDescription;

    @ApiModelProperty(value = "资源详细描述")
    @Column(name = "`long_description`")
    private String longDescription;

    @ApiModelProperty(value = "照片场景时间时间撮返回")
    @Column(name = "`generate_time`")
    private Date generateTime;

    @ApiModelProperty(value = "照片场景时间String")
    @Transient
    private String generateTimeStr;

    @ApiModelProperty(value = "照片的内容场景(游泳、玩具、吃奶、发烧等生活事件)")
    @Column(name = "`content_type`")
    private String contentType;

    @Transient
    @ApiModelProperty(value = "资源所在的组文件夹名称")
    private String bucketFolderName;

    @Transient
    private List<Map<String,Object>> uploadFileUrl;

    @Transient
    private Integer bucketFolderId;

    public Resource()
    {
        super(0);
        this.setCreateTime(new Date());
        this.setOperator("system");
    }
}
