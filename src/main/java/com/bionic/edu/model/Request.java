package com.bionic.edu.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by VPortianko on 09.11.2015.
 */
@Entity
@Table(name = "requests")
public class Request extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    @NotNull
    @Column(name = "pic_url")
    private String picUrl;

    public Request() {};

    public Request(Integer id, User user, String description, LocalDateTime dateTime, String picUrl) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.dateTime = dateTime;
        this.picUrl = picUrl;
    }
    public Request(Integer id, User user, String description, String picUrl) {
        this.id = id;
        this.description = description;
        this.picUrl = picUrl;
    }

    public Request(Request request) {
        this.id = request.getId();
        this.user = request.getUser();
        this.description = request.getDescription();
        this.dateTime = request.getDateTime();
        this.picUrl = request.getPicUrl();
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
