package com.devdmin.core.service.impl;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.businessvalidator.BusinessValidator;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SportFieldServiceImpl implements SportFieldService{

    @Autowired
    private SportFieldRepository repository;

    @Autowired
    private BusinessValidator<SportField,User> validator;

    @Autowired
    private UserService userService;
    @Override
    public SportField find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public SportField add(SportField sportField) {
        if(validator.validateAdding(sportField, sportField.getAuthor()))
            return userService.addSportField(sportField, sportField.getAuthor());
        else
            return null;
    }

    @Override
    public SportField update(Long id, SportField sportField) {
        SportField sportFieldToUpdate = find(id);
        sportFieldToUpdate.update(sportField);
        return repository.save(sportFieldToUpdate);
    }

    @Override
    public SportField delete(SportField sportField) {
        sportField.getAuthor().getOwnSportFields().remove(sportField);
        sportField.setAuthor(null);

        repository.delete(sportField);
        return sportField;
    }

    @Override
    public SportField verify(SportField sportField) {
        sportField.setVerified(true);
        return repository.save(sportField);
    }

    @Override
    public List<SportField> findAll() {
        return repository.findAll();
    }
}
