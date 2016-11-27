package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.repository.SportFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("sportField")
@RestController
public class SportFieldController {
    private SportFieldRepository sportFieldRepository;

    @Autowired
    public SportFieldController(SportFieldRepository sportFieldRepository){
        this.sportFieldRepository = sportFieldRepository;
    }

    @GetMapping
    List<SportField> findAll(){ return sportFieldRepository.findAll(); }

}
