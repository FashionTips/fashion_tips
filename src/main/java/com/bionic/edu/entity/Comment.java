package com.bionic.edu.entity;

import javax.persistence.*;

/**
 * Comment Entity.
 * Created by maxim on 11/5/15.
 * TODO: create post property
 * TODO: create user property
 */
@Entity
@Table(name = "comment")
@NamedQuery(name = "Comment.getAll", query = "SELECT c from Comment c")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;

    @OneToOne
    private User user;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
