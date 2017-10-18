package com.devdmin.core.businessvalidator;

public interface BusinessRule<T,R> {
    boolean validate(T t, R r);
}
