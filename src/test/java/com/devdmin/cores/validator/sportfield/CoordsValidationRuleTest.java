package com.devdmin.cores.validator.sportfield;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.sportfield.CoordsValidationRule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoordsValidationRuleTest extends SportFieldValidationTest{

    @Override
    void setRule(Rule rule) {
        super.rule = new CoordsValidationRule();
    }

    //Cords from Poland
    @Test
    public void testValidCoords(){
        sportField = new SportField(51.216947, 19.037782, SportFieldType.BASKETBALL);

        rule.validate(sportField, errors);
        assertFalse(errors.hasErrors());
    }

    //Cords from Germany
    @Test
    public void testInvalidCoords(){
        sportField = new SportField(52.557307, 14.551229, SportFieldType.BASKETBALL);

        rule.validate(sportField, errors);
        assertTrue(errors.hasErrors());
    }


}
