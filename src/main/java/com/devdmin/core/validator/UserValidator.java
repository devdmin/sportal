package com.devdmin.core.validator;

import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * This class provides a implementation of the {@link Validator}
 * interface for {@link User} forms
 *
 * @author Damian Ujma
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private List<Rule<UserDto>> rules;
    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto user = (UserDto) o;

        for(Rule<UserDto> rule : rules){
            rule.validate(user, errors);
        }

    }
}
