package com.devdmin.core.service.util;

import com.devdmin.core.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    private List<Event> events = new ArrayList<>();

    public EventList(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

}
