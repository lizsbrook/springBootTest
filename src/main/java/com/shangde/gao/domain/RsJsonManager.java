package com.shangde.gao.domain;


import com.shangde.gao.domain.log.LoggerInfo;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/21
 * Time: 下午5:00
 * 返回数据管理类
 */
public class RsJsonManager {

    private static ThreadLocal<BaseResultJson> resultJsonThreadLocal =new ThreadLocal<>();

    private static ThreadLocal<LoggerInfo> loggerInfoThreadLocal = new ThreadLocal<>();

    public static ResDTO success(Object data, String... successMessage) {
        return getResultJson().reDataSuccess(data, successMessage);
    }

    public static ResDTO successDate(Object data, String... successMessage) {
        return RsJsonManager.getResultJson().reDataSuccess(data, successMessage);
    }

    public static ResDTO error(String... errorMessage) {
        return getResultJson().reError(errorMessage);
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


    public static LoggerInfo getLoggerInfo() {
        LoggerInfo loggerInfo = loggerInfoThreadLocal.get();
        if (null == loggerInfo) {
            loggerInfo = new LoggerInfo();
            loggerInfoThreadLocal.set(loggerInfo);
        }
        return loggerInfo;
    }

}
