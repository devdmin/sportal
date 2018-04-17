package com.devdmin.core.businessvalidator;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * This class provides a skeletal implementation of the {@link ModelBusinessValidator}
 * interface to minimize the effort required to implement this interface
 *
 * @author Damian Ujma
 */
public abstract class BusinessValidator<T,R> implements ModelBusinessValidator<T, R>{
    @Autowired
    private List<BusinessRule<T,R>> rules;

    public boolean validateAdding(R r) {
        for(BusinessRule<T, R> rule : rules){
            if(!rule.validateAdding(r))
                return false;
        }
        return true;
    }
}
