package com.devdmin.cores.validator;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class UserValidatorTest {
    @InjectMocks
    UserValidator userValidator;
    @Mock
    private UserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidUser() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        createdUser.setGender(Gender.MALE);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutGender() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutUsername() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutEmail() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("");
        createdUser.setPassword("password");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutPassword() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser,errors);
        assertTrue(errors.hasErrors());
    }
    @Test
    public void testValidationWithExistingUsername() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");
        when(service.find(any(String.class))).thenReturn(createdUser);

        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }


    @Test
    public void testValidationWithInvalidUsername() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("qwertyuiopasdfghjklzxcvbnm");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");

        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithExistingEmail() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");
        when(service.findByEmail(any(String.class))).thenReturn(createdUser);

        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIvalidEmail() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("mail.mail.pl");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");


        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIvalidAge1() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(5);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");


        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }
    @Test
    public void testValidationWithIvalidAge2() throws Exception{
        User createdUser = new User();
        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(101);
        Errors errors = new BeanPropertyBindingResult(createdUser, "createdUser");


        userValidator.validate(createdUser, errors);
        assertTrue(errors.hasErrors());
    }


}
