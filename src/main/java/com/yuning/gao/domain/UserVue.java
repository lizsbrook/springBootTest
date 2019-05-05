package com.yuning.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 17:44 2018/12/18
 * Description：${description}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserVue{
    private List<String> roles;

    private String token;

    private String introduction;

    private String avatar;

    private String name;
}
