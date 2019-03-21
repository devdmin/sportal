package com.devdmin.rest.controller.dto;


import com.devdmin.core.model.util.Gender;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Set;

public class EventDto {
    private Long id;
    private Set<UserDto> users;

    @JsonSerialize
    private LocalDateTime date;
    private LocalDateTime endDate;

    private int minAge;
    private int maxAge;
    private int maxMembers;
    private Gender gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
