package com.bionic.edu.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

/**
 * Created by VPortianko on 04.11.2015.
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_email")})
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    protected String email;

    @Column(name = "password", nullable = false)
    @NotEmpty
    protected String password;

    @Column(name = "name", nullable = false)
    @NotEmpty
    protected String name;

    @Column(name = "registered")
    protected LocalDateTime registered = LocalDateTime.now();

    public User() {}

    public User(Integer id, String email, String password, String name, LocalDateTime registered) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.registered = registered;
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.registered = user.getRegistered();
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", registered=" + registered +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }
}
