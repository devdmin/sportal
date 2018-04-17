package com.devdmin.core.businessvalidator;

/**
 * <code>Validator</code> for business model forms.
 *
 * @author Damian Ujma
 */
public interface ModelBusinessValidator<T,R> {
    boolean validateAdding(R r);
}
