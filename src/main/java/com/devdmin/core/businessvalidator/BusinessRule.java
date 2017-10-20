package com.devdmin.core.businessvalidator;

public interface BusinessRule<T,R> {
    boolean validateAdding(T t, R r);
}
