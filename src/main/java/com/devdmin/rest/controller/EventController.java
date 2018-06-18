package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;


import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.util.EventList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
/**
 * @author Damian Ujma
 */
@RequestMapping("api/events")
@RestController
public class EventController {
    private final EventService eventService;

    @Autowired
    @Qualifier("eventValidator")
    private Validator eventValidator;

    @InitBinder
    public void dataBinding(WebDataBinder binder){
        binder.setValidator(eventValidator);
    }

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{sportFieldId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<Event> add(@PathVariable Long sportFieldId, @RequestBody @Valid Event sentEvent, BindingResult result) {
        if(result.hasErrors()){
            System.out.println(result.toString());
            return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
        }else {
            sentEvent.setEventAuthor(getUser());
            Event event = eventService.add(sentEvent, sportFieldId);
            return new ResponseEntity<Event>(event, HttpStatus.CREATED);
        }
    }

    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<EventList> findAll(){
        EventList eventList = new EventList(eventService.findAll());
        return new ResponseEntity<EventList>(eventList, HttpStatus.OK);
    }

    @GetMapping("/sportField/{sportFieldId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<EventList> findBySportFieldId(@PathVariable Long sportFieldId){
        EventList eventList = new EventList(eventService.findBySportFieldId(sportFieldId));
        return new ResponseEntity<EventList>(eventList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<Event> get(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    System.out.println(event.getSportField().toString());
                    return new ResponseEntity<Event>(event, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event sentEvent){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    Event updatedEvent = eventService.update(id,sentEvent);
                    return new ResponseEntity<Event>(updatedEvent, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> delete(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    eventService.delete(event.getId());
                    return new ResponseEntity<Event>(event, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }

    public User getUser() {
        AccountUserDetails user = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
