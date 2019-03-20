package com.devdmin.core.validator.rules.user;

import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ExistingValidationRule implements Rule<UserDto> {
    @Autowired
    private UserService service;

    @Override
    public void validate(UserDto user, Errors errors) {
        if(service.findByEmail(user.getEmail()) != null || service.find(user.getUsername()) != null){
            errors.rejectValue("email","negativevalue");
        }
    }
}
