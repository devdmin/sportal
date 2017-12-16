package com.devdmin.core.model;

import com.devdmin.core.model.util.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "sportfield_id")
    @JsonBackReference
    private SportField sportField;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private List<User> users;
    private LocalDateTime date;
    private LocalDateTime endDate;
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
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
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

    public void update(Event event){
        this.date = event.getDate();
        this.minAge = event.getMinAge();
        this.maxAge = event.getMaxAge();
        this.gender = event.getGender();
        this.endDate = event.getEndDate();
        this.maxMembers = event.getMaxMembers();
    }
}
