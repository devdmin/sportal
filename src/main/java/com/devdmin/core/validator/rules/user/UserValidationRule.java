package com.devdmin.core.validator.rules.user;

import com.devdmin.core.model.User;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class UserValidationRule implements Rule<UserDto> {

    @Override
    public void validate(UserDto user, Errors errors) {
        if(user.getAge() < 6 || user.getAge() > 100){
            errors.rejectValue("age", "negativevalue");
        }
        if(user.getUsername().length() > 24){
            errors.rejectValue("username","negativevalue");
        }
    }
}
