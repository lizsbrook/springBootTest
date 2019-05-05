package com.yuning.gao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "`lite_sys_user`")
@NoArgsConstructor
@Data
//后台登录用户实体类
public class LoginUser extends MetaEntity {

    @ApiModelProperty(value = "用户名")
    @Column(name = "`name`")
    private String name;

    @ApiModelProperty(value = "密码")
    @Column(name = "`password`")
    private String password;

    @ApiModelProperty(value = "用户状态 0激活1禁用")
    @Column(name = "`state`")
    private Integer state;

    public LoginUser(String name,String password) {
        super(0);
        this.state = 0;
        this.name = name;
        this.password = password;
    }
}
