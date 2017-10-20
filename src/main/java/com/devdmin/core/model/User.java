package com.devdmin.core.model;

import com.devdmin.core.model.util.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    private LocalDate signUpDate;
    private int age;
    private Gender gender;
    @OneToMany(mappedBy = "author", cascade =  {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Event> ownEvents;
    @OneToMany(mappedBy = "author", cascade =  {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SportField> ownSportFields;
    @ManyToMany(mappedBy = "users")
    private List<Event> events;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "member")
    private List<SportField> sportFields;
    private boolean isVerified;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(LocalDate signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<SportField> getSportFields() {
        return sportFields;
    }

    public void setSportFields(List<SportField> sportFields) {
        this.sportFields = sportFields;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public List<SportField> getOwnSportFields() {
        return ownSportFields;
    }

    public void setOwnSportFields(List<SportField> ownSportFields) {
        this.ownSportFields = ownSportFields;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public List<Event> getOwnEvents() {
        return ownEvents;
    }

    public void setOwnEvents(List<Event> ownEvents) {
        this.ownEvents = ownEvents;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", signUpDate=" + signUpDate +
                ", age=" + age +
                ", gender=" + gender +
                ", ownSportFields=" + ownSportFields +
                ", events=" + events +
                ", sportFields=" + sportFields +
                ", isVerified=" + isVerified +
                ", token='" + token + '\'' +
                '}';
    }
}
