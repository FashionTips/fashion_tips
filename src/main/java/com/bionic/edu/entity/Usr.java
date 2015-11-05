package com.bionic.edu.entity;

import javax.persistence.*;

/**
 * Usr Entity.
 * Created by maxim on 11/4/15.
 * TODO: List<Post> property
 * TODO: List<Comment> property
 * TODO: create follows property
 * TODO: create followers property
 */
@Entity
@NamedQuery(name = "User.getAll", query = "SELECT u from Usr u")
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String login;
    private String email;
    private String password;
    private String description;

    public Usr() {
    }

    public Usr(String name, String login, String email, String password, String description) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Usr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
