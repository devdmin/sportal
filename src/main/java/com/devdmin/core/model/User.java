package com.devdmin.core.model;

import com.devdmin.core.model.util.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * Simple JavaBean domain object representing a user.
 *
 * @author Damian Ujma
 */
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
    @OneToMany(mappedBy = "eventAuthor", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonManagedReference(value="user-movement")
    private Set<Event> ownEvents;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonManagedReference
    private Set<SportField> ownSportFields;
    @ManyToMany(mappedBy = "users")
    private List<Event> events;
    private boolean isVerified;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID token;

    @OneToOne(mappedBy = "user")
    private Logging logging;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    public String getPassword() {
        return password;
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

    public Gender getGender() {
        return gender;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Set<SportField> getOwnSportFields() {
        return ownSportFields;
    }

    public void setOwnSportFields(Set<SportField> ownSportFields) {
        this.ownSportFields = ownSportFields;
    }

    public UUID getToken() {
        return token;
    }

    @JsonIgnore
    public Set<Event> getOwnEvents() {
        return ownEvents;
    }

    public void setOwnEvents(Set<Event> ownEvents) {
        this.ownEvents = ownEvents;
    }

    public void update(User user) {
        this.age = user.getAge();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.password = user.getPassword();
    }

    public User(){
        this.signUpDate = LocalDate.now();
        this.token = UUID.randomUUID();
        this.isVerified = false;
    }
    public User(String username, String password, int age, Gender gender, String email){
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.signUpDate = LocalDate.now();
        this.token = UUID.randomUUID();
        this.isVerified = false;
    }

}
