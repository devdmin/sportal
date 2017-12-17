package com.devdmin.cores.validator;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.*;
import com.devdmin.core.validator.rules.*;
import com.devdmin.core.validator.rules.user.EmailValidatationRule;
import com.devdmin.core.validator.rules.user.ExistingValidationRule;
import com.devdmin.core.validator.rules.user.FilledFieldsRule;
import com.devdmin.core.validator.rules.user.UserValidationRule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.validation.*;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class UserValidatorTest {
    @InjectMocks
    private UserValidator userValidator;

    @InjectMocks
    private ExistingValidationRule existingValidationRule;

    @Mock
    private UserService service;

    @Spy
    private List<Rule<User>> rules = new ArrayList<Rule<User>>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        rules.add(new EmailValidatationRule());
        rules.add(existingValidationRule);
        rules.add(new FilledFieldsRule());
        rules.add(new UserValidationRule());
    }

    @Test
    public void testValidUser() throws Exception{

        User createdUser = new User("test","testtest",24, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutGender() throws Exception{
        User createdUser = new User("test","testtest",24, null,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutUsername() throws Exception{
        User createdUser = new User("","testtest",24, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutEmail() throws Exception{
        User createdUser = new User("test","testtest",24, Gender.MALE,"");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutPassword() throws Exception{
        User createdUser = new User("test",null,24, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }
    @Test
    public void testValidationWithExistingUsername() throws Exception{
        User createdUser = new User("test","testtest",24, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");
        when(service.find(any(String.class))).thenReturn(createdUser);

        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }


    @Test
    public void testValidationWithInvalidUsername() throws Exception{

        User createdUser = new User("qwertyuiopasdfghjklzxcvbnm","testtest",24, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithExistingEmail() throws Exception{
        User createdUser = new User("test","testtest",24, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");
        when(service.findByEmail(any(String.class))).thenReturn(createdUser);

        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIvalidEmail() throws Exception{
        User createdUser = new User("test","testtest",24, Gender.MALE,"mail.mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");


        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIvalidAge1() throws Exception{
        User createdUser = new User("test","testtest",5, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");


        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }
    @Test
    public void testValidationWithIvalidAge2() throws Exception{
        User createdUser = new User("test","testtest",102, Gender.MALE,"mail@mail.pl");
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");


        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }


}
