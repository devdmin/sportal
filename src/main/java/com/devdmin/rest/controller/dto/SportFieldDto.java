package com.devdmin.rest.controller.dto;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class SportFieldDto {
    private Long id;
    private double lat;
    private double lng;
    private SportFieldType type;
    private Set<EventDto> events;
    private LocalDate addingDate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public SportFieldType getType() {
        return type;
    }

    public void setType(SportFieldType type) {
        this.type = type;
    }

    public Set<EventDto> getEvents() {
        return events;
    }

    public void setEvents(Set<EventDto> events) {
        this.events = events;
    }

    public void setAddingDate(LocalDate addingDate) {
        this.addingDate = addingDate;
    }

    public LocalDate getAddingDate() {
        return addingDate;
    }


}
