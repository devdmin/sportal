package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.SportFieldDtoList;
import com.devdmin.rest.controller.dto.SportFieldDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * @author Damian Ujma
 */
@RequestMapping("api/sportField")
@RestController
public class SportFieldController {
    @Autowired
    private SportFieldService sportFieldService;
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    @Qualifier("sportFieldValidator")
    private Validator sportFieldValidator;

    @Autowired
    private BaseConverter<SportField, SportFieldDto> sportFieldDomainConverter;

    @Autowired
    private BaseConverter<SportFieldDto, SportField> sportFieldDtoConverter;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.setValidator(sportFieldValidator);
    }

    @GetMapping
    public ResponseEntity<SportFieldDtoList> findAll(){
        SportFieldDtoList sportFieldList = new SportFieldDtoList(sportFieldDomainConverter.convertAll(sportFieldService.findAll()));
        return new ResponseEntity<SportFieldDtoList>(sportFieldList, HttpStatus.OK);
    }

    @GetMapping("event/{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<SportFieldDto> findSportFieldByEventId(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    return new ResponseEntity<SportFieldDto>(sportFieldDomainConverter.convert(event.getSportField()), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportFieldDto>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<SportFieldDto> add(@RequestBody @Valid SportFieldDto sportField){
        SportField domain = sportFieldDtoConverter.convert(sportField);
        domain.setAuthor(getUser());
        SportField addedSportfield = sportFieldService.add(domain);

        return new ResponseEntity<SportFieldDto>(sportFieldDomainConverter.convert(addedSportfield), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<SportFieldDto> get(@PathVariable Long id){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    return new ResponseEntity<SportFieldDto>(sportFieldDomainConverter.convert(sportField), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportFieldDto>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}/verify")
    @PreAuthorize("permitAll")
    public ResponseEntity<SportFieldDto> verify(@PathVariable Long id){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    SportField verifiedSportField = sportFieldService.verify(sportField);
                    return new ResponseEntity<SportFieldDto>(sportFieldDomainConverter.convert(verifiedSportField), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportFieldDto>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportFieldDto> update(@PathVariable Long id, @RequestBody SportField sentSportField){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    SportField updatedSportField = sportFieldService.update(id,sentSportField);
                    return new ResponseEntity<SportFieldDto>(sportFieldDomainConverter.convert(updatedSportField), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportFieldDto>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SportFieldDto> delete(@PathVariable Long id){
        return Optional.ofNullable(sportFieldService.find(id))
                .map(sportField -> {
                    SportField deletedSportField = sportFieldService.delete(sportField);
                    return new ResponseEntity<SportFieldDto>(sportFieldDomainConverter.convert(deletedSportField), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<SportFieldDto>(HttpStatus.NOT_FOUND));
    }


    public User getUser() {
        AccountUserDetails user = (AccountUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
