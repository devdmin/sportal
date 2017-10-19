package com.devdmin.core.businessvalidator;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BusinessValidator<T,R> implements ModelBusinessValidator<T, R>{
    @Autowired
    private List<BusinessRule<T,R>> rules;

    @Override
    public boolean validate(T t, R r) {
        for(BusinessRule<T, R> rule : rules){
            if(rule.validate(t,r))
                return false;
        }
        return true;
    }
}
