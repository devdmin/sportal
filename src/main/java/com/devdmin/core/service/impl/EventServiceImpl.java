package com.devdmin.core.service.impl;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SportFieldRepository sportFieldRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
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
    public Event add(Event event, Long sportFieldId) {
        SportField sportField = sportFieldRepository.findOne(sportFieldId);
        event.setSportField(sportField);
        return eventRepository.save(event);
    }
}
