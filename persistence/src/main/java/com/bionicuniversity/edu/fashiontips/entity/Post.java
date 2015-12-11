package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity Class Post whish mapped on post table in DB
 *
 * @author Alexandr
 * @author Volodymyr Portianko
 * @since 25.11.2015
 */
@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {

    /**
     * Column user, which consists User Entity
     */
    @JsonProperty("author")
    @JsonIgnoreProperties(value = {"id", "email", "password"})
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Column created which represent creating date of post
     * Auto convert by LocalDateTimeConverter
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "created", nullable = false, insertable = false)
    private LocalDateTime created;

    /**
     * Column TextMessage which represent user's ext-message
     */
    @NotBlank(message = "Post body could not be empty.")
    @Size(max = 1000, message = "Post body may not has more than 1000 characters.")
    @Column(name = "user_post")
    private String textMessage;

    @NotBlank(message = "Title could not be empty.")
    @Size(max = 100, message = "Title may not has more than 100 characters.")
    @Column(name = "title")
    private String title;

    /**
     * Column Category referred on Category enum with post's categories
     * Auto convert by CategoryConverter
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = true, insertable = true)
    private Category category;

    /*
* List of posts images
* Relationships store in separate table
* */
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "post_images",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "img_id")
    )
    private Set<Image> images;

    /**
     * Default Constructor
     */
    public Post() {
    }

    /**
     * @param user    - referenced to User Entity
     * @param message - consists user's message
     */
    public Post(User user, String title, String message, Category category) {
        this.user = user;
        this.title = title;
        this.textMessage = message;
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                //", created=" + created +
                ", title=" + title +
                ", textMessage='" + textMessage + '\'' +
                ", category=" + category +
                ", images=" + images +
                '}';
    }


    /**
     * Enum Category represent categories of user's posts.
     * It can be Question about user's outfit,
     * or typically user post with some images
     *
     * @author Alexandr
     * @author Maksym Dolia
     * @since 28.11.2015
     */
    public enum Category {
        POST, QUESTION
    }
}
