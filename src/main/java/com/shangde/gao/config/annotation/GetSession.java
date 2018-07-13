package com.shangde.gao.config.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/7/19
 * Time: 下午5:02
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetSession {

}
