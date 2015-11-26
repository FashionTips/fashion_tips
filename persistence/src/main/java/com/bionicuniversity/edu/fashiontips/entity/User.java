package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for User.
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    private String login;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    public User() {
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + super.getId() + '\'' +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                '}';
    }
}
