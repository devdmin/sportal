package com.devdmin.core.model;

import com.devdmin.core.LocalDateAttributeConverter;
import com.devdmin.core.LocalDateTimeAttributeConverter;
import com.devdmin.core.model.util.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Simple JavaBean domain object representing an event.
 *
 * @author Damian Ujma
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "sportfield_id")
    @JsonBackReference(value="sportfield-movement")
    private SportField sportField;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Set<User> users;
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime date;
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime endDate;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate addingDate;
    private int minAge;
    private int maxAge;
    private int maxMembers;
    private Gender gender;

    @JsonBackReference(value="user-movement")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User eventAuthor;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public SportField getSportField() {
        return sportField;
    }

    public void setSportField(SportField sportField) {
        this.sportField = sportField;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User getEventAuthor() {
        return eventAuthor;
    }

    public void setEventAuthor(User author) {
        this.eventAuthor = author;
    }

    public LocalDate getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(LocalDate addingDate) {
        this.addingDate = addingDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }
    public Event(){
        this.addingDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (minAge != event.minAge) return false;
        if (maxAge != event.maxAge) return false;
        if (maxMembers != event.maxMembers) return false;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (sportField != null ? !sportField.equals(event.sportField) : event.sportField != null) return false;
        if (users != null ? !users.equals(event.users) : event.users != null) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;
        if (addingDate != null ? !addingDate.equals(event.addingDate) : event.addingDate != null) return false;
        if (gender != event.gender) return false;
        return eventAuthor != null ? eventAuthor.equals(event.eventAuthor) : event.eventAuthor == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sportField != null ? sportField.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (addingDate != null ? addingDate.hashCode() : 0);
        result = 31 * result + minAge;
        result = 31 * result + maxAge;
        result = 31 * result + maxMembers;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (eventAuthor != null ? eventAuthor.hashCode() : 0);
        return result;
    }

    public Event(LocalDateTime date, LocalDateTime endDate, int minAge, int maxAge, Gender gender, int maxMembers, User author){
        this.date = date;
        this.endDate = endDate;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.gender = gender;
        this.maxMembers = maxMembers;
        this.addingDate = LocalDate.now();
        this.eventAuthor = author;
    }
    public void update(Event event){
        this.date = event.getDate();
        this.minAge = event.getMinAge();
        this.maxAge = event.getMaxAge();
        this.gender = event.getGender();
        this.endDate = event.getEndDate();
        this.maxMembers = event.getMaxMembers();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", sportField=" + sportField +
                ", users=" + users +
                ", date=" + date +
                ", endDate=" + endDate +
                ", addingDate=" + addingDate +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", maxMembers=" + maxMembers +
                ", gender=" + gender +
                ", eventAuthor=" + eventAuthor +
                '}';
    }


}
