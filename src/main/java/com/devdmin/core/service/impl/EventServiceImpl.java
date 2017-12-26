package com.devdmin.core.service.impl;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.exceptions.DateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SportFieldRepository sportFieldRepository;

    @Autowired
    private UserRepository userRepository;

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
        Event foundEvent = eventRepository.findOne(id);
        foundEvent.update(event);
        return eventRepository.save(foundEvent);
    }

    @Override
    public Event delete(Long id) {
        eventRepository.delete(id);
        return find(id);
    }

    @Override
    public Event add(Event event, Long sportFieldId) {
        SportField foundSportField = sportFieldRepository.findOne(sportFieldId);
        User foundUser = userRepository.findByUsername(event.getEventAuthor().getUsername());
        event.setAddingDate(LocalDate.now());
        Set<Event> events = Optional.ofNullable(foundSportField.getEvents())
                .orElse(new HashSet<Event>());

        Set<Event> userEvents = Optional.ofNullable(foundUser.getOwnEvents())
                .orElse(new HashSet<Event>());

        events.add(event);
        userEvents.add(event);
        event.setEventAuthor(foundUser);
        return eventRepository.save(event);
    }

    public List<Event> findBySportFieldId(Long sportFieldId){
        return eventRepository.findBySportField_Id(sportFieldId);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

//    @Override
//    public List<Event> findEventsByDateTimeBetween(LocalDateTime date, LocalDateTime endDate) {
//        return eventRepository.findByDateTimeBetween(date, endDate);
//    }

}
