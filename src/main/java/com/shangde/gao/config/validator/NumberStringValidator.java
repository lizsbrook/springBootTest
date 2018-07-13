package com.shangde.gao.config.validator;


import com.shangde.gao.config.annotation.IsNumberStr;
import org.springframework.util.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NumberStringValidator implements ConstraintValidator<IsNumberStr,String> {

    private static Pattern pattern = Pattern.compile("[0-9]+");

    @Override
    public void initialize(IsNumberStr isNumberStr) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !StringUtils.isEmpty(s) && pattern.matcher(s).matches();
    }
}
