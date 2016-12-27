package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.util.SportFieldList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/sportField")
@RestController
public class SportFieldController {
   private SportFieldService sportFieldService;

    @Autowired
    public SportFieldController(SportFieldService sportFieldService){
        this.sportFieldService = sportFieldService;
    }

    @GetMapping
    public ResponseEntity<SportFieldList> findAll(){
        SportFieldList sportFieldList = new SportFieldList(sportFieldService.findAll());
        return new ResponseEntity<SportFieldList>(sportFieldList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SportField> add(@RequestBody SportField sportField){
        sportFieldService.add(sportField);
        return new ResponseEntity<SportField>(sportField, HttpStatus.CREATED);
    }
}
