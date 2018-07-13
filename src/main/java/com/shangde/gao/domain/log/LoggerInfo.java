package com.shangde.gao.domain.log;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/6/4
 * Time: 下午5:41
 */
public class LoggerInfo {

    //执行时间
    private String executeTime;
    //请求url
    private String url;
    //请求真实ip
    private String realIp;
    //请求参数
    private StringBuilder param;
    //请求结果
    private String result;
    //当前登录用的session
    private String session;


    public void putNull() {
        executeTime = null;
        url = null;
        realIp = null;
        param = null;
        result = null;
        session = null;
    }

    @Override
    public String toString() {
        return "{" +
                " url='" + url + "\n" +
                ", realIp='" + realIp + "\n" +
                ", param='" + param + "\n" +
                ", result='" + result + "\n" +
                ", executeTime='" + executeTime + "\n" +
                ", session='" + session + "\n" +
                '}';
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    public StringBuilder getParam() {
        return param;
    }

    public void setParam(StringBuilder param) {
        this.param = param;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
