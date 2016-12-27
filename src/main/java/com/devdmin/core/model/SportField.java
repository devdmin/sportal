package com.devdmin.core.model;

import com.devdmin.core.model.util.SportFieldType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class SportField {
    @Id
    @GeneratedValue
    private Long id;
    private String coords;
    private SportFieldType type;

    @OneToMany(mappedBy = "sportField")
    private List<Event> events;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public SportFieldType getType() {
        return type;
    }

    public void setType(SportFieldType type) {
        this.type = type;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
