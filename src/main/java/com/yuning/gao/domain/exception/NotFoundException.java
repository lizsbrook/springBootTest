package com.yuning.gao.domain.exception;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/6/10
 * Time: 下午1:58
 */
public class NotFoundException extends  RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

}
