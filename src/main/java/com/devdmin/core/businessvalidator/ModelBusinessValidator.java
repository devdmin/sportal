package com.devdmin.core.businessvalidator;

public interface ModelBusinessValidator<T,R> {
    boolean validateAdding(R r);
}
