package com.devdmin.core.validator;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserRepository userRepository;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");

        ValidationUtils.rejectIfEmpty(errors, "username", "username.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        User user = (User) o;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());

        if(user.getAge() < 6 || user.getAge() > 100){
            errors.rejectValue("age", "negativevalue");
        }
        if(userRepository.findByUsername(user.getUsername()) != null || user.getUsername().length() > 24){
            errors.rejectValue("username","negativevalue");
        }

        if(!matcher.matches() || userRepository.findByEmail(user.getEmail()) != null){
            errors.rejectValue("email","negativevalue");
        }
    }
}
