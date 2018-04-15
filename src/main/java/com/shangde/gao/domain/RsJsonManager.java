package com.shangde.gao.domain;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/21
 * Time: 下午5:00
 * 返回数据管理类
 */
public class RsJsonManager {

    private static ThreadLocal<BaseResultJson> resultJsonThreadLocal =new ThreadLocal<>();

    public static BaseResultJson getResultJson(){
        BaseResultJson resultJson = resultJsonThreadLocal.get();
        if(null == resultJson){
            resultJson = new BaseResultJson();
            resultJsonThreadLocal.set(resultJson);
        }
        return resultJson;
    }
}
