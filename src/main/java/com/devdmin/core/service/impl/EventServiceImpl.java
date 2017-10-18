package com.devdmin.core.service.impl;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.exceptions.DateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
        Event foundEvent = eventRepository.findOne(id);
        foundEvent.setDate(event.getDate());
        foundEvent.setMinAge(event.getMinAge());
        foundEvent.setMaxAge(event.getMaxAge());
        foundEvent.setGender(event.getGender());
        foundEvent.setEndDate(event.getEndDate());
        foundEvent.setMaxMembers(event.getMaxMembers());
        return eventRepository.save(foundEvent);
    }

    @Override
    public Event delete(Long id) {
        eventRepository.delete(id);
        return find(id);
    }

    @Override
    public Event add(Event event, Long sportFieldId) {
        SportField sportField = sportFieldRepository.findOne(sportFieldId);
        sportField.getEvents();
        for (Event foundEvent : sportField.getEvents()) {
            if(event.getDate().isBefore(foundEvent.getDate()) && event.getEndDate().isBefore(foundEvent.getDate()) ||
                    event.getDate().isAfter(foundEvent.getEndDate()) && event.getEndDate().isAfter(foundEvent.getEndDate())){
                event.setSportField(sportField);
                event.setAddingDate(LocalDate.now());
            }else{
                throw new DateException();
            }
        }
        return eventRepository.save(event);
    }

    public List<Event> findBySportFieldId(Long sportFieldId){
        return eventRepository.findBySportField_Id(sportFieldId);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }


}
