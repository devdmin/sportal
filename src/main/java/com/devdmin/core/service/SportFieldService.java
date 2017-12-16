package com.devdmin.core.service;


import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;

import java.util.List;

public interface SportFieldService {
    SportField find(Long id);
    SportField add(SportField sportField);
    SportField update(Long id, SportField sportField);
    SportField delete(SportField sportField);
    SportField verify(SportField sportField);
    List<SportField> findAll();
}
