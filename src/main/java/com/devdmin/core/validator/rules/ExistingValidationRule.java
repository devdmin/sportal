package com.devdmin.core.validator.rules;

import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ExistingValidationRule implements Rule<User> {
    @Autowired
    private UserService service;

    @Override
    public void validate(User user, Errors errors) {
        if(service.findByEmail(user.getEmail()) != null || service.find(user.getUsername()) != null){
            errors.rejectValue("email","negativevalue");
        }
    }
}
