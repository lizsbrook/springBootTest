package com.yuning.gao.domain;

import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/2/28
 * Time: 下午8:15
 */
@Table(name = "lite_sys_user_role")
public class UserRole extends MetaEntity {

    private Integer roleId;

    private String userName;

    public UserRole() {

    }

    public UserRole(Integer roleId) {
        this.roleId = roleId;
    }

    public UserRole(String userName, Integer roleId) {
        this.userName = userName;
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
