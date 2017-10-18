package com.devdmin.core.businessvalidator;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SportFieldBusinessValidator implements ModelBusinessValidator<SportField,User> {

    @Autowired
    private List<BusinessRule<SportField,User>> rules;

    @Override
    public boolean validate(SportField sportField, User user) {
        for(BusinessRule<SportField, User> rule : rules){
            if(rule.validate(sportField,user))
                return false;
        }
        return true;
    }
}
