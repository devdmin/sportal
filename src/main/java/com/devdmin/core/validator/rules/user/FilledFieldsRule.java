package com.devdmin.core.validator.rules.user;

import com.devdmin.core.model.User;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class FilledFieldsRule implements Rule<UserDto> {
    @Override
    public void validate(UserDto regData, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "username", "username.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
    }
}
