package com.devdmin.core.businessvalidator;

public interface ModelBusinessValidator<T,R> {
    boolean validateAdding(T t, R r);
}
