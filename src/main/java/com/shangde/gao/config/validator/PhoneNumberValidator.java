package com.shangde.gao.config.validator;


import com.shangde.gao.config.annotation.IsPhoneNumber;
import org.springframework.util.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/5/24
 * Time: 下午3:28
 */
public class PhoneNumberValidator implements ConstraintValidator<IsPhoneNumber,String> {
    @Override
    public void initialize(IsPhoneNumber isPhoneNumber) {

    }

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return mobile.length() == 11;
    }
}
