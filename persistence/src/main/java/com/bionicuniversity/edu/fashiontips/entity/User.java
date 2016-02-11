package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.annotation.Create;
import com.bionicuniversity.edu.fashiontips.annotation.UniqueEmail;
import com.bionicuniversity.edu.fashiontips.annotation.UniqueLogin;
import com.bionicuniversity.edu.fashiontips.annotation.Update;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateSerializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class for User which mapped on user table in DB
 */
@Entity
@Table(name = "users")
@SqlResultSetMapping(name = "post.user.followers", classes = {
        @ConstructorResult(targetClass = User.class, columns = {@ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "login", type = String.class),
                @ColumnResult(name = "img_id", type = Long.class),
                @ColumnResult(name = "img_name", type = String.class)})
})
public class User extends BaseEntity<Long> {

    @NotBlank(message = "Login could not be empty.", groups = {Create.class, Update.class})
    @Size(min = 4, max = 32, message = "Login cannot has more than 32 characters, and be less than 4.",
            groups = {Create.class, Update.class})
    @UniqueLogin(groups = Create.class)
    private String login;

    @NotBlank(message = "Email could not be empty.", groups = {Create.class})
    @Email(message = "The given string is not email.", groups = {Create.class, Update.class})
    @UniqueEmail(groups = Create.class)
    private String email;

    @NotBlank(message = "Password could not be empty.", groups = {Create.class})
    @Size(min = 4, max = 32, message = "Password has to have 4 to 32 characters.",
            groups = {Create.class, Update.class})
    private String password;

    @Fetch(FetchMode.SELECT)
    @JsonIgnoreProperties("id")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    /* User profile's data */

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created", nullable = false, insertable = false)
    private LocalDateTime created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_images",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "img_id")
    )
    private Image avatar;

    private String firstName, lastName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    private Boolean hideAge;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String location;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String occupation;

    private String aboutMe;

    private String instagram;

    private URL blogUrl;

    private URL websiteUrl;

    /**
     * Default Constructor
     */
    public User() {
    }

    public User(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(Long id, String login, Long imgId, String imgName) {
        this.id = id;
        this.login = login;
        this.avatar = new Image(imgId,imgName);
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

    public User(final Long id, final String login, final String email, final String password, final List<Role> roles) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonProperty
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean isHideAge() {
        return hideAge;
    }

    public void setHideAge(Boolean hideAge) {
        this.hideAge = hideAge;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public URL getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(URL blogUrl) {
        this.blogUrl = blogUrl;
    }

    public URL getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(URL websiteUrl) {
        this.websiteUrl = websiteUrl;
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

    /**
     * Representation of human genger. Could have one of two options: GUY and GIRL.
     */
    public enum Gender {
        GIRL, GUY
    }
}
