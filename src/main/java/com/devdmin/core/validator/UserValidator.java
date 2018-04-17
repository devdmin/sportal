package com.devdmin.core.validator;

import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
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
    private List<Rule<User>> rules;
    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        for(Rule<User> rule : rules){
            rule.validate(user, errors);
        }

    }
    public static boolean validateAddingSportField(User user){
        return false;
    }
}
