package com.devdmin.core.businessvalidator;

public interface BusinessRule<T,R> {
    boolean validateAdding(R r);
}
