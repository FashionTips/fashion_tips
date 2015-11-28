package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for User which mapped on user table in DB
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    private String login;
    private String email;
    private String password;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    private Set<Post> posts = new HashSet<>();

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     *
     * @param login - Represent user login
     * @param email - Represent user email
     * @param password - Represent user password
     */
    public User(final String login, final String email, final String password) {
        this.login = login;
        this.email = email;
        this.password = password;
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

//    public Set<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(Set<Post> posts) {
//        this.posts = posts;
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
