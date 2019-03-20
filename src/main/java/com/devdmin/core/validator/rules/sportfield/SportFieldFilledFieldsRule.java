package com.devdmin.core.validator.rules.sportfield;

import com.devdmin.core.model.SportField;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.SportFieldDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class SportFieldFilledFieldsRule implements Rule<SportFieldDto> {
    @Override
    public void validate(SportFieldDto regData, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "lat", "sportField.empty");
        ValidationUtils.rejectIfEmpty(errors, "lng", "sportField.empty");
        ValidationUtils.rejectIfEmpty(errors, "type", "sportField.empty");

    }
}
