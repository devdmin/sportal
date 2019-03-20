package com.devdmin.cores.validator;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.validator.SportFieldValidator;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.sportfield.CoordsValidationRule;
import com.devdmin.core.validator.rules.sportfield.SportFieldFilledFieldsRule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SportFieldValidatorTest {
    @InjectMocks
    private SportFieldValidator sportFieldValidator;

    @Mock
    private SportFieldService service;

    @Spy
    private List<Rule<SportField>> rules = new ArrayList<>();

    private Errors errors;
    private SportField sportField;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        rules.add(new SportFieldFilledFieldsRule());
        rules.add(new CoordsValidationRule());
        sportField = new SportField(52.782831, 21.972656, SportFieldType.BASKETBALL);
        errors = new BeanPropertyBindingResult(sportField, "sportField");
    }

    @Test
    public void testValidSportField(){
        sportField = new SportField(52.782831, 21.972656, SportFieldType.BASKETBALL);

        sportFieldValidator.validate(sportField, errors);
        assertFalse(errors.hasErrors());
    }


    //Illegal Coords from France
    @Test
    public void testValidationWithIllegalCoords(){
         sportField = new SportField(47.380700, 5.879242, SportFieldType.BASKETBALL);

        sportFieldValidator.validate(sportField, errors);
        assertTrue(errors.hasErrors());
    }
}
