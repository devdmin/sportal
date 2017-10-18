package com.devdmin.core.businessvalidator;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SportFieldBusinessValidator implements ModelBusinessValidator<SportField,User> {

    @Override
    public boolean validate(SportField sportField, User user) {

        return false;
    }
}
