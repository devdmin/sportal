package com.devdmin.core.validator.rules.sportfield;

import com.devdmin.core.model.SportField;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class SportFieldFilledFieldsRule implements Rule<SportField> {
    @Override
    public void validate(SportField regData, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "lat", "sportField.empty");
        ValidationUtils.rejectIfEmpty(errors, "lng", "sportField.empty");
        ValidationUtils.rejectIfEmpty(errors, "type", "sportField.empty");

        System.out.println(errors.getFieldValue("lat"));
    }
}
