package com.shangde.gao.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/21
 * Time: 下午5:03
 */
public class BaseResultJson {
    private ResDTO resDTO = new ResDTO();

    /**
     * Re success res dto.
     * 返回成功
     *
     * @param successMessage the success message
     * @return the res dto
     * @author yf -wenHao
     * @date 2017 -10-21 17:08:50
     */
    public ResDTO reSuccess(String... successMessage){
        if(successMessage.length <=0){
            resDTO.setRsdesp("成功");
        }else {
            resDTO.setRsdesp(successMessage[0]);
        }
        resDTO.setResultMessage("");
        resDTO.setRs(1);
        return resDTO;
    }

    /**
     * Re success res dto.
     * 返回成功
     *
     * @param data           the data
     * @param successMessage the success message
     * @return the res dto
     * @author yf -wenHao
     * @date 2017 -10-21 17:08:51
     */
    public ResDTO reDataSuccess(Object data, String... successMessage){
        if(successMessage.length <=0){
            resDTO.setRsdesp("成功");
        }else {
            resDTO.setRsdesp(successMessage[0]);
        }
        resDTO.setResultMessage(data);
        resDTO.setRs(1);
        return resDTO;
    }


    /**
     * Re map data success res dto.
     * 返回map 包装类型
     * @param key   the key map key
     * @param value the value map value
     * @return the res dto
     * @author hero
     * @date 2017 -12-26 14:05:22
     */
    public ResDTO reMapDataSuccess(String key, Object value) {
        Map<String, Object> resMp = new HashMap<>();
        resMp.put("roomUrl", value);
        resDTO.setRsdesp("成功");
        resDTO.setResultMessage(resMp);
        resDTO.setRs(1);
        return resDTO;
    }


    /**
     * Re error res dto.
     * 返回错误
     * @param errorMessage the error message
     * @return the res dto
     * @author yf-wenHao
     * @date 2017 -10-21 17:08:51
     */
    public ResDTO reError(String... errorMessage){
        if(errorMessage.length <=0){
            resDTO.setRsdesp("失败");
        }else {
            resDTO.setRsdesp(errorMessage[0]);
        }
        resDTO.setResultMessage("");
        resDTO.setRs(0);
        return  resDTO;
    }

}
