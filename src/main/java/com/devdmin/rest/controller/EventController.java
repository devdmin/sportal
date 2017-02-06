package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;
import com.devdmin.core.repository.EventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/events")
@RestController
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    List<Event> findAll() { return eventRepository.findAll(); }

    @GetMapping("/{id}")
    Event findEventById(@PathVariable Long id){
        return eventRepository.findOne(id);
    }


}
