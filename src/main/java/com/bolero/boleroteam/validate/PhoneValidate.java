package com.bolero.boleroteam.validate;

import com.bolero.boleroteam.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PhoneValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
//        User phoneNumber = (User) target;
//        String number = phoneNumber.getPhone();
//        ValidationUtils.rejectIfEmpty(errors,"phoneNumber","number.empty");
//        if (number.length() > 11 || number.length()<10){
//            errors.rejectValue("phoneNumber","number.length");
//        }
//        if (!number.startsWith("0")){
//            errors.rejectValue("phoneNumber","number.startsWith");
//        }
//        if (!number.matches("(^$|[0-9]*$)")){
//            errors.rejectValue("phoneNumber","number.matches");
//        }
    }
}
