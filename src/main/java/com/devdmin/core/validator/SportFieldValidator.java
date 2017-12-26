package com.devdmin.core.validator;


import com.devdmin.core.model.SportField;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SportFieldValidator implements Validator {
    @Autowired
    private List<Rule<SportField>> rules;

    @Override
    public boolean supports(Class<?> aClass) {
        return SportField.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SportField sportField = (SportField) o;

        for(Rule<SportField> rule : rules){
            rule.validate(sportField, errors);
        }
    }
}