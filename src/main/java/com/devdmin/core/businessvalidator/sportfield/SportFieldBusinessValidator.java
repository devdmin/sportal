package com.devdmin.core.businessvalidator.sportfield;

import com.devdmin.core.businessvalidator.BusinessValidator;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class provides a implementation of the {@link com.devdmin.core.businessvalidator.ModelBusinessValidator}
 * interface for {@link User} forms
 *
 * @author Damian Ujma
 */
@Component
public class SportFieldBusinessValidator extends BusinessValidator<SportField,User> {
}
