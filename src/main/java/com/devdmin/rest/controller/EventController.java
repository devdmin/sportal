package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;


import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.EventDtoList;
import com.devdmin.core.service.util.UserDtoList;
import com.devdmin.rest.controller.dto.EventDto;
import com.devdmin.rest.controller.dto.PostDto;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
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
    private BaseConverter<Event, EventDto> eventDomainConverter;

    @Autowired
    private BaseConverter<User, UserDto> userDomainConverter;


    @Autowired
    private BaseConverter<EventDto, Event> eventDtoConverter;

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
    public ResponseEntity<EventDto> add(@PathVariable Long sportFieldId, @RequestBody @Valid EventDto sentEvent, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<EventDto>(HttpStatus.BAD_REQUEST);
        }else {
            Event eventDomain = eventDtoConverter.convert(sentEvent);
            eventDomain.setEventAuthor(getUser());
            EventDto event = eventDomainConverter.convert(eventService.add(eventDomain, sportFieldId));
            return new ResponseEntity<EventDto>(event, HttpStatus.CREATED);
        }
    }

    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<EventDtoList> findAll(){
        EventDtoList eventList = new EventDtoList(eventDomainConverter.convertAll(eventService.findAll()));
        return new ResponseEntity<EventDtoList>(eventList, HttpStatus.OK);
    }

    @GetMapping("/sportField/{sportFieldId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<EventDtoList> findBySportFieldId(@PathVariable Long sportFieldId){
        EventDtoList eventList = new EventDtoList(eventDomainConverter.convertAll(eventService.findBySportFieldId(sportFieldId)));
        return new ResponseEntity<EventDtoList>(eventList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll")
    public ResponseEntity<EventDto> get(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    return new ResponseEntity<EventDto>(eventDomainConverter.convert(event), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<EventDto>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{id}/author")
    @PreAuthorize("permitAll")
    public ResponseEntity<UserDto> getAuthor(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    return new ResponseEntity<UserDto>(userDomainConverter.convert(event.getEventAuthor()), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<UserDtoList> getParticipants(@PathVariable Long id) {
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    return new ResponseEntity<UserDtoList>(new UserDtoList(userDomainConverter.convertAll(event.getUsers())), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<UserDtoList>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody EventDto sentEvent){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    EventDto updatedEvent = eventDomainConverter.convert(eventService.update(id, eventDtoConverter.convert(sentEvent)));
                    return new ResponseEntity<EventDto>(updatedEvent, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<EventDto>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventDto> delete(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    eventService.delete(event.getId());
                    return new ResponseEntity<EventDto>(eventDomainConverter.convert(event), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<EventDto>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/join")
    @PreAuthorize("permitAll")
    public ResponseEntity<EventDto> join(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    event = eventService.join(event, userService.find(getUser().getUsername()));
                    return new ResponseEntity<EventDto>(eventDomainConverter.convert(event), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<EventDto>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/join")
    @PreAuthorize("permitAll")
    public ResponseEntity<EventDto> signOut(@PathVariable Long id){
        return Optional.ofNullable(eventService.find(id))
                .map(event -> {
                    event = eventService.signOut(event, userService.find(getUser().getUsername()));
                    return new ResponseEntity<EventDto>(eventDomainConverter.convert(event), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<EventDto>(HttpStatus.NOT_FOUND));
    }


    public User getUser() {
        AccountUserDetails user = (AccountUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
