package com.yuning.gao.config.annotation;

import com.yuning.gao.config.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/5/24
 * Time: 下午3:26
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface IsPhoneNumber {
    String message() default "不符合手机号规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
