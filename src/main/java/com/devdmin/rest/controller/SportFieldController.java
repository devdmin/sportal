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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("permitAll")
    public ResponseEntity<SportField> add(@RequestBody SportField sportField){
//        User author = userService.find(getAccountName());
        SportField addedSportfield = sportFieldService.add(sportField);
//        if(author.getSportFields() == null){
//            List<SportField> toAdd = new ArrayList<>();
//            toAdd.add(addedSportfield);
//            author.setSportFields(toAdd);
//        }else{
//            List<SportField> toAdd = author.getSportFields();
//            toAdd.add(addedSportfield);
//            author.setSportFields(toAdd);
//        }
//        userService.save(author);
        return new ResponseEntity<SportField>(addedSportfield, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<SportField> get(@PathVariable Long id){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    return new ResponseEntity<SportField>(sportField, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportField>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportField> update(@PathVariable Long id, @RequestBody SportField sentSportField){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    SportField updatedSportField = sportFieldService.update(id,sentSportField);
                    return new ResponseEntity<SportField>(updatedSportField, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportField>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SportField> delete(@PathVariable Long id){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    sportFieldService.delete(sportField.getId());
                    return new ResponseEntity<SportField>(sportField, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportField>(HttpStatus.NOT_FOUND));
    }


}
