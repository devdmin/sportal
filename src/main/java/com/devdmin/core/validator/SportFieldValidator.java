package com.devdmin.core.validator;


import com.devdmin.core.model.SportField;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.SportFieldDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * This class provides a implementation of the {@link Validator}
 * interface for {@link SportField} forms
 *
 * @author Damian Ujma
 */
@Component
public class SportFieldValidator implements Validator {
    @Autowired
    private List<Rule<SportFieldDto>> rules;

    @Override
    public boolean supports(Class<?> aClass) {
        return SportFieldDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SportFieldDto sportField = (SportFieldDto) o;

        for(Rule<SportFieldDto> rule : rules){
            rule.validate(sportField, errors);
        }

    }
}
