package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;


import com.devdmin.core.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

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
}
