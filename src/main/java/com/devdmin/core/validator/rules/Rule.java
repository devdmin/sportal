package com.devdmin.core.validator.rules;

import org.springframework.validation.Errors;

public interface Rule<T> {
    void validate(T regData, Errors errors);
}
