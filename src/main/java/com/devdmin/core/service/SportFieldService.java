package com.devdmin.core.service;


import com.devdmin.core.model.SportField;

import java.util.List;

public interface SportFieldService {
    SportField find(Long id);
    SportField add(SportField sportField);
    SportField update(Long id, SportField sportField);
    SportField delete(Long id);
    List<SportField> findAll();
}
