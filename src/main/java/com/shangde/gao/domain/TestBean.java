package com.shangde.gao.domain;

public class TestBean {

    private Integer id;
    private String teacherName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
