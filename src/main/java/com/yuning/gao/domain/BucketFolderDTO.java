package com.yuning.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 18:44 2018/8/17
 * Description：${description}
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BucketFolderDTO {

    private BucketFolder bucketFolder;

    private List<Resource> resourceList;
}
