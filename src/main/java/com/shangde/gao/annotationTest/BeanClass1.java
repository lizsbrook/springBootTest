package com.shangde.gao.annotationTest;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "lite_user_sign_up")
public class BeanClass1 {

    public BeanClass1(String openId, Integer liveCourseId, String formId) {
        this.liveCourseId = liveCourseId;
        this.openId = openId;
        this.formId = formId;
    }

    @Column(name = "open_id")
    private String openId;

    @Column(name = "live_course_id")
    private int liveCourseId;

    @Column(name = "form_id")
    private String formId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getLiveCourseId() {
        return liveCourseId;
    }

    public void setLiveCourseId(int liveCourseId) {
        this.liveCourseId = liveCourseId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
