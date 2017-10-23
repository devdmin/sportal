package com.devdmin.core.businessvalidator;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SportFieldBusinessValidator extends BusinessValidator<SportField,User> {
}
