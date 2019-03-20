package com.devdmin.core.service.util;

import com.devdmin.core.model.Event;
import com.devdmin.rest.controller.dto.EventDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDtoList {
    private Set<EventDto> events = new HashSet<>();

    public EventDtoList(Set<EventDto> events) {
        this.events = events;
    }

    public Set<EventDto> getEvents() {
        return events;
    }

}
