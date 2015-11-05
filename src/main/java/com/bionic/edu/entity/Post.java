package com.bionic.edu.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Post Entity.
 * Created by maxim on 11/4/15.
 * TODO: add Usr property
 * TODO: add List<Item> property
 * TODO: add List<Comment> property
 */
@Entity
@NamedQuery(name = "Post.getAll", query = "SELECT p FROM Post p")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private int likes;
    private Date date;

    public Post() {
    }

    public Post(String description, int likes, Date date) {
        this.description = description;
        this.likes = likes;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                '}';
    }
}
