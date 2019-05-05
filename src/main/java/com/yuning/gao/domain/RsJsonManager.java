package com.yuning.gao.domain;


/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/21
 * Time: 下午5:00
 * 返回数据管理类
 */
public class RsJsonManager {


    private static ThreadLocal<BaseResultJson> resultJsonThreadLocal =new ThreadLocal<>();


    private static ThreadLocal<String> resultStringThreadLocal = new ThreadLocal<>();

    public static <T> ResDTO<T> success() {
        return RsJsonManager.getResultJson().reSuccess();
    }


    public  static <T> ResDTO<T> successDate(T t) {
        return RsJsonManager.getResultJson().reDataSuccess(t);
    }

    public  static <T> ResDTO<T> error(String message) {
        return RsJsonManager.getResultJson().reError(message);
    }

    public static BaseResultJson getResultJson(){
        BaseResultJson resultJson = resultJsonThreadLocal.get();
        if(null == resultJson){
            resultJson = new BaseResultJson();
            resultJsonThreadLocal.set(resultJson);
        }
        return resultJson;
    }

    public static ResDTO reNewError(String... errorMessage) {
        ResDTO dto = new ResDTO();
        if (errorMessage.length <= 0) {
            dto.setRsdesp("失败");
        } else {
            dto.setRsdesp(errorMessage[0]);
        }
        dto.setResultMessage("");
        dto.setRs(0);
        return  dto;
    }

    public static  String getResult() {
        return resultStringThreadLocal.get();
    }
}
