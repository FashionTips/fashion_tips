package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.annotation.Create;
import com.bionicuniversity.edu.fashiontips.annotation.UniqueEmail;
import com.bionicuniversity.edu.fashiontips.annotation.UniqueLogin;
import com.bionicuniversity.edu.fashiontips.annotation.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Entity class for User which mapped on user table in DB
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @NotBlank(message = "Login could not be empty.", groups = {Create.class, Update.class})
    @Size(min = 4, max = 32, message = "Login cannot has more than 32 characters, and be less than 4.",
            groups = {Create.class, Update.class})
    @UniqueLogin(groups = Create.class)
    private String login;

    @NotBlank(message = "Email not be empty.", groups = {Create.class, Update.class})
    @Email(message = "The given string is not email.", groups = {Create.class, Update.class})
    @UniqueEmail(groups = Create.class)
    private String email;

    @NotBlank(message = "Password could not be empty.", groups = {Create.class, Update.class})
    @Size(min = 4, max = 32, message = "Password has to have 4 to 32 characters.",
            groups = {Create.class, Update.class})
    private String password;

    @JsonIgnoreProperties("id")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Constructs entity with given login, email and password.
     *
     * @param login    login
     * @param email    email
     * @param password password
     */
    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public User(final Long id, final String login, final String email, final String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
