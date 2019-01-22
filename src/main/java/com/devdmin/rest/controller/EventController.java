package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;


import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.EventList;
import com.devdmin.core.service.util.UserList;
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
    private final UserService userService;

    @Autowired
    @Qualifier("eventValidator")
    private Validator eventValidator;

    @InitBinder
    public void dataBinding(WebDataBinder binder){
        binder.setValidator(eventValidator);
    }

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping("/{sportFieldId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<Event> add(@PathVariable Long sportFieldId, @RequestBody @Valid Event sentEvent, BindingResult result) {
        if(result.hasErrors()){
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
                    return new ResponseEntity<Event>(event, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{id}/author")
    @PreAuthorize("permitAll")
    public ResponseEntity<User> getAuthor(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    return new ResponseEntity<User>(event.getEventAuthor(), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<UserList> getParticipants(@PathVariable Long id) {
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    return new ResponseEntity<UserList>(new UserList(event.getUsers()), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<UserList>(HttpStatus.NOT_FOUND));
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

    @PostMapping("/{id}/join")
    @PreAuthorize("permitAll")
    public ResponseEntity<Event> join(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    System.out.println(event);
                    event = eventService.join(event, userService.find(getUser().getUsername()));
                    return new ResponseEntity<Event>(event, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/join")
    @PreAuthorize("permitAll")
    public ResponseEntity<Event> signOut(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    event = eventService.signOut(event, userService.find(getUser().getUsername()));
                    return new ResponseEntity<Event>(event, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }


    public User getUser() {
        AccountUserDetails user = (AccountUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
