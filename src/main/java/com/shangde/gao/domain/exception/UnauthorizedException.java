package com.shangde.gao.domain.exception;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/6/10
 * Time: 下午4:04
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
