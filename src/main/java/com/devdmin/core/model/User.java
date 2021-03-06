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
    @OneToMany(mappedBy = "eventAuthor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonManagedReference(value="user-movement")
    @JsonIgnore
    private Set<Event> ownEvents;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonManagedReference
    private Set<SportField> ownSportFields;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "event_user",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<Event> events;
    private boolean isVerified;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID token;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonBackReference(value="author-movement")
    @JsonIgnore
    private Set<Post> ownPosts;

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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    //
//    public Set<Post> getOwnPosts() {
//        return ownPosts;
//    }
//
//    public void setOwnPosts(Set<Post> ownPosts) {
//        this.ownPosts = ownPosts;
//    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (isVerified != user.isVerified) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (signUpDate != null ? !signUpDate.equals(user.signUpDate) : user.signUpDate != null) return false;
        if (gender != user.gender) return false;
        if (ownEvents != null ? !ownEvents.equals(user.ownEvents) : user.ownEvents != null) return false;
        if (ownSportFields != null ? !ownSportFields.equals(user.ownSportFields) : user.ownSportFields != null)
            return false;
        return token != null ? token.equals(user.token) : user.token == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (signUpDate != null ? signUpDate.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (isVerified ? 1 : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
