package com.devdmin.core.model;


import com.devdmin.core.LocalDateTimeAttributeConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Simple JavaBean domain object representing a post.
 *
 * @author Damian Ujma
 */
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonManagedReference(value="author-movement")
    private User author;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime date;

    private short rate;

    private String text;

    public Post() {
        this.date =  LocalDateTime.now();
        this.rate = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public short getRate() {
        return rate;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (rate != post.rate) return false;
        if (id != null ? !id.equals(post.id) : post.id != null) return false;
        if (author != null ? !author.equals(post.author) : post.author != null) return false;
        if (event != null ? !event.equals(post.event) : post.event != null) return false;
        return date != null ? date.equals(post.date) : post.date == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) rate;
        return result;
    }
}
