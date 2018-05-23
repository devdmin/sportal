package com.devdmin.core.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Simple JavaBean domain object representing a logging.
 *
 * @author Damian Ujma
 */
@Entity
public class Logging {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String ip;
    private LocalDateTime date;

    public Logging() {
    }

    public Logging(User user, String ip) {
        this.user = user;
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
