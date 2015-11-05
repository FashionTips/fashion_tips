package com.bionic.edu.entity;

import javax.persistence.*;

/**
 * Comment Entity.
 * Created by maxim on 11/5/15.
 * TODO: add post property
 * TODO: add user property
 */
@Entity
@NamedQuery(name = "Comment.getAll", query = "SELECT c from Comment c")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
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
}
