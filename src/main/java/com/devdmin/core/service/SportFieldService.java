package com.devdmin.core.service;


import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;

import java.util.List;

public interface SportFieldService {
    SportField find(Long id);
    SportField add(SportField sportField, User author);
    SportField update(Long id, SportField sportField);
    SportField delete(Long id);
    List<SportField> findAll();
}
