package com.yuning.gao.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 15:17 2018/10/3
 * Description：${description}
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "lite_user_operate_log")
@Data
@NoArgsConstructor
public class UserOperateLog extends MetaEntity {


    public UserOperateLog(String type, String openid,String category1) {
        this.type = type;
        this.openid = openid;
        this.category1 = category1;
    }

    @Column(name = "`type`")
    private String type;

    @Column(name = "`openid`")
    private String openid;

    @Column(name = "`category1`")
    private String category1;

}
