package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.SportFieldList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/sportField")
@RestController
public class SportFieldController {
    @Autowired
    private SportFieldService sportFieldService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<SportFieldList> findAll(){
        SportFieldList sportFieldList = new SportFieldList(sportFieldService.findAll());
        return new ResponseEntity<SportFieldList>(sportFieldList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SportField> add(@RequestBody SportField sportField){
        User author = userService.find(getAccountName());
        SportField addedSportfield = sportFieldService.add(sportField);
        if(author.getSportFields() == null){
            List<SportField> toAdd = new ArrayList<>();
            toAdd.add(addedSportfield);
            author.setSportFields(toAdd);
        }else{
            List<SportField> toAdd = author.getSportFields();
            toAdd.add(addedSportfield);
            author.setSportFields(toAdd);
        }
        userService.save(author);
        return new ResponseEntity<SportField>(sportField, HttpStatus.CREATED);
    }

    private String getAccountName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            return details.getUsername();
        }
        return null;
    }
}
