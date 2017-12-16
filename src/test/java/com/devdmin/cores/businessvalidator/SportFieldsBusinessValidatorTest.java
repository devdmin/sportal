package com.devdmin.cores.businessvalidator;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.businessvalidator.DailyLimitRule;
import com.devdmin.core.businessvalidator.SportFieldBusinessValidator;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SportFieldsBusinessValidatorTest {

    @InjectMocks
    private SportFieldBusinessValidator sportFieldValidator;

    @InjectMocks
    private DailyLimitRule limitRule;

    @Mock
    private UserService userService;

    @Spy
    private List<BusinessRule<SportField, User>> rules = new ArrayList<BusinessRule<SportField,User>>();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        rules.add(limitRule);

    }

    @Test
    public void testValidAddingSportField(){
        User user = new User();
        user.setUsername("user");
        SportField sportField = new SportField();
        sportField.setAddingDate(LocalDate.of(1990,1,1));
        user.setOwnSportFields(new HashSet<>(Arrays.asList(sportField)));
        when(userService.find(any(Long.class))).thenReturn(user);
        assertTrue(sportFieldValidator.validateAdding(user));
    }

    @Test
    public void testIvalidAddingSportField(){
        User user = new User();
        user.setUsername("user");
        SportField sportField = new SportField();
        sportField.setAddingDate(LocalDate.now());
        user.setOwnSportFields(new HashSet<>(Arrays.asList(sportField)));
        when(userService.find(any(Long.class))).thenReturn(user);
        assertFalse(sportFieldValidator.validateAdding(user));
    }
}
