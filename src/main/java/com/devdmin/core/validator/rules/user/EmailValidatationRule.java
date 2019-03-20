package com.devdmin.core.validator.rules.user;

import com.devdmin.core.model.User;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidatationRule implements Rule<UserDto> {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    public void validate(UserDto user, Errors errors) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
        if(!matcher.matches()){
            errors.rejectValue("email","negativevalue");
        }
    }
}
