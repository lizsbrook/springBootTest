package com.yuning.gao.domain;
/**
 * create by: gaoming01
 * description:基本实体类
 * create time: 21:50 2018/9/30
 */
public class ResDTO<T> {

    //是否成功 1 是，0 否
    private int rs;
    //返回数据实体类
    private T resultMessage;
    //返回结果描述
    private String rsdesp;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public T getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(T resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getRsdesp() {
        return rsdesp;
    }

    public void setRsdesp(String rsdesp) {
        this.rsdesp = rsdesp;
    }
}