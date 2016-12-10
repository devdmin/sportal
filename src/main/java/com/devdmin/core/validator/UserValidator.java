package com.devdmin.core.validator;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        ValidationUtils.rejectIfEmpty(errors, "username", "username.empty");
        User user = (User) o;
        if(user.getAge() < 6){
            errors.rejectValue("age", "negativevalue");
        } else if(user.getAge() > 110){
            errors.rejectValue("age", "negativevalue");
        }

        if(userRepository.findByUsername(user.getUsername()) != null){
            errors.rejectValue("username","negativevalue");
        }

        if(userRepository.findByEmail(user.getEmail()) != null){
            errors.rejectValue("email","negativevalue");
        }
    }
}
