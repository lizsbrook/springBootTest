package com.shangde.gao.domain;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/2/28
 * Time: 下午8:12
 */
@Table(name = "lite_sys_role")
public class Role extends MetaEntity {

    public Role(){
        super(0);
    }

    public Role(String roleName) {
        super(0);
        this.roleName = roleName;
    }


    private String roleName;

    private String roleDesc;


    @Transient
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
