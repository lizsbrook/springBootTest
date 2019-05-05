package com.yuning.gao.domain.exception;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/6/19
 * Time: 下午2:47
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
