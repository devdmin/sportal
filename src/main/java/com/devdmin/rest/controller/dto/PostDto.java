package com.devdmin.rest.controller.dto;

import com.devdmin.core.model.Post;

import java.time.LocalDateTime;

public class PostDto {

    private Long id;
    private UserDto author;
    private LocalDateTime date;
    private short rate;
    private String text;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
