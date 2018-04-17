package com.devdmin.core.businessvalidator;

/**
 * A business rule for application-specific objects.
 *
 * <p>Rule determines whether given object has correct data in comparison with other data.</p>
 *
 * @author Damian Ujma
 */
public interface BusinessRule<T,R> {
    boolean validateAdding(R r);
}
