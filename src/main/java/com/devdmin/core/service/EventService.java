package com.devdmin.core.service;

import com.devdmin.core.model.Event;

public interface EventService {
    Event save(Event event);
    Event find(Long id);
    Event update(Long id, Event event);
    Event add(Event event, Long sportFieldId);
}
