package com.devdmin.core.businessvalidator;

public interface ModelBusinessValidator<T,R> {
    boolean validate(T t, R r);
}
