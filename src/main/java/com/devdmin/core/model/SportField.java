package com.devdmin.core.model;

import com.devdmin.core.model.util.SportFieldType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
/**
 * Simple JavaBean domain object representing a sportfield.
 *
 * @author Damian Ujma
 */
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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private User author;
    @OneToMany(mappedBy = "sportField", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonManagedReference(value="sportfield-movement")
    private Set<Event> events;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }


    public double getLng() {
        return lng;
    }


    public SportFieldType getType() {
        return type;
    }


    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public SportField() {
        this.addingDate = LocalDate.now();
        this.isVerified = false;
    }

    public SportField(double lat, double lng, SportFieldType type) {
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.addingDate = LocalDate.now();
        this.isVerified = false;
    }

    public void update(SportField sportField) {
        this.lat = sportField.getLat();
        this.lng = sportField.getLng();
        this.events = sportField.getEvents();
        this.type = sportField.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SportField that = (SportField) o;

        if (Double.compare(that.lat, lat) != 0) return false;
        if (Double.compare(that.lng, lng) != 0) return false;
        if (isVerified != that.isVerified) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != that.type) return false;
        if (addingDate != null ? !addingDate.equals(that.addingDate) : that.addingDate != null) return false;
        return author != null ? author.equals(that.author) : that.author == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isVerified ? 1 : 0);
        result = 31 * result + (addingDate != null ? addingDate.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}
