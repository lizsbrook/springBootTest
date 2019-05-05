package com.yuning.gao.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 11:38 2018/7/13
 * Description：${description}
 */
@Data
@Table(name = "`lite_sys_user`" )
public class SysUser extends MetaEntity{

    public SysUser() {

    }
    public SysUser(String name) {
        super(0);
        this.name = name;
    }
    public SysUser(String name,String password) {
        super(0);
        this.name = name;
        this.password = password;
    }
    @Column(name ="`name`")
    private String name;

    @Column(name ="`password`")
    private String password;

    @Column(name ="`state`")
    private Integer state;

}
