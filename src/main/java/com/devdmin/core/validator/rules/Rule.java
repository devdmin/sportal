package com.devdmin.core.validator.rules;

import org.springframework.validation.Errors;
/**
 * A rule for application-specific objects.
 *
 * <p>Rule determines whether given object has correct data.</p>
 *
 * @author Damian Ujma
 */
public interface Rule<T> {

    /**
     * Validate the supplied target object.
     *
     * @param regData the object that is to be validated (can be null)
     * @param errors contextual state about the validation process
     */
    void validate(T regData, Errors errors);
}
