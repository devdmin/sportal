package com.devdmin.core.service;

import com.devdmin.core.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event save(Event event);
    Event find(Long id);
    Event update(Long id, Event event);
    Event delete(Long id);
    Event add(Event event, Long sportFieldId);
    List<Event> findBySportFieldId(Long sportFieldId);
    List<Event> findAll();
   // boolean existingCollision(Event event);
}
