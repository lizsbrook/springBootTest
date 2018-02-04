package com.shangde.gao.domain;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/21
 * Time: 下午4:57
 */
public class ResDTO {

    //是否成功 1 是，0 否
    private int rs;
    //返回数据实体类
    private Object resultMessage;
    //返回结果描述
    private String rsdesp;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public Object getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(Object resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getRsdesp() {
        return rsdesp;
    }

    public void setRsdesp(String rsdesp) {
        this.rsdesp = rsdesp;
    }
}