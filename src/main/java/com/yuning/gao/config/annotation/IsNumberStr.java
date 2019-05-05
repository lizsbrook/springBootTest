package com.yuning.gao.config.annotation;



import com.yuning.gao.config.validator.NumberStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming
 * Date: 18/6/19
 * Time: 下午3:26
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NumberStringValidator.class)
public @interface IsNumberStr {
    String message() default "非数字类型的字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}