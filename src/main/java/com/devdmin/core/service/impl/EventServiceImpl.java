package com.devdmin.core.service.impl;

import com.devdmin.core.model.Event;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event save(Event user) {
        return eventRepository.save(user);
    }

    @Override
    public Event find(Long id) {
        return eventRepository.findOne(id);
    }

    @Override
    public Event update(Long id, Event event) {
        return null;
    }

    @Override
    public Event add(Event event) {
        return eventRepository.save(event);
    }
}
