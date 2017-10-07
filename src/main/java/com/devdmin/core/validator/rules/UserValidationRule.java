package com.devdmin.core.validator.rules;

import com.devdmin.core.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidationRule implements Rule<User> {

    @Override
    public void validate(User user, Errors errors) {
        if(user.getAge() < 6 || user.getAge() > 100){
            errors.rejectValue("age", "negativevalue");
        }
        if(user.getUsername().length() > 24){
            errors.rejectValue("username","negativevalue");
        }
    }
}
