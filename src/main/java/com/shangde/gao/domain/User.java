package com.shangde.gao.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "`lite_user`")
public class User extends MetaEntity {

    public User(){}

    public User(String openId) {
        this.openid = openId;
    }

    public User(String openid, String unionid) {
        this.openid = openid;
        this.unionid = unionid;
    }

    @Column(name = "`openid`")
    private String openid;

    @Column(name = "`unionid`")
    private String unionid;

    @Column(name = "`mobile`")
    private String mobile;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`nickname`")
    private String nickname;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;

    //解密偏移量
    @Transient
    private String iv;
    //加密后的手机号数据
    @Transient
    private String encryptedData;
    //秘钥 解密用
    @Transient
    private String sessionKey;

    //小程序渠道码-个人中心
    @Transient
    private String channelCode = "Sv-wechatApp";


}