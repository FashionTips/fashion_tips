package com.bionic.edu.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by VPortianko on 09.11.2015.
 */
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    public Comment() {}

    public Comment(Integer id, Request request, User user, String commentText, LocalDateTime dateTime) {
        this.id = id;
        this.request = request;
        this.user = user;
        this.commentText = commentText;
        this.dateTime = dateTime;
    }

    public Comment(Integer id, Request request, String commentText) {
        this.id = id;
        this.request = request;
        this.commentText = commentText;
    }

    public Comment(Comment comment) {
        this.id = comment.getId();
        this.request = comment.getRequest();
        this.user = comment.getUser();
        this.commentText = comment.getCommentText();
        this.dateTime = comment.getDateTime();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
