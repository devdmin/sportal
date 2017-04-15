package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;


import com.devdmin.core.service.EventService;
import com.devdmin.core.service.util.EventList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/events")
@RestController
public class EventController {

    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{sportFieldId}")
    @PreAuthorize("permitAll")
    public ResponseEntity<Event> add(@PathVariable Long sportFieldId, @RequestBody Event sentEvent) {
        Event event = eventService.add(sentEvent, sportFieldId);
        return new ResponseEntity<Event>(event, HttpStatus.CREATED);
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
}
