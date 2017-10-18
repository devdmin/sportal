package com.devdmin.core.service.impl;

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
    private UserService userService;
    @Override
    public SportField find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public SportField add(SportField sportField, User user) {
        sportField.setAddingDate(LocalDate.now());
        return userService.addSportField(sportField, user);
    }

    @Override
    public SportField update(Long id, SportField sportField) {
        SportField sportFieldToUpdate = find(id);
        sportFieldToUpdate.setLat(sportField.getLat());
        sportFieldToUpdate.setLng(sportField.getLng());
        sportFieldToUpdate.setEvents(sportField.getEvents());
        sportFieldToUpdate.setType(sportField.getType());
        return repository.save(sportFieldToUpdate);
    }

    @Override
    public SportField delete(Long id) {
        repository.delete(id);
        return find(id);
    }

    @Override
    public List<SportField> findAll() {
        return repository.findAll();
    }
}
