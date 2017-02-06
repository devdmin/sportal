package com.devdmin.core.model;

import com.devdmin.core.model.util.SportFieldType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class SportField {
    @Id
    @GeneratedValue
    private Long id;
    private double lat;
    private double lng;
    private SportFieldType type;
    private boolean isVerified;
    private LocalDate addingDate;
//    @OneToMany
//    @JoinColumn(name = "author_id")
//    private User author;
    @OneToMany(mappedBy = "sportField")
    private List<Event> events;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public LocalDate getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(LocalDate addingDate) {
        this.addingDate = addingDate;
    }
//
//    public User getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(User author) {
//        this.author = author;
//    }
}
