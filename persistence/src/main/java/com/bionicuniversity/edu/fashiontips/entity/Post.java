package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity Class Post whish mapped on post table in DB
 * @author Alexandr
 * @since 25.11.2015
 */
@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {

    /**
     * Column user, which consists User Entity
     */
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Column created which represent creating date of post
     * Auto convert by LocalDateTimeConverter
     */
    @Column(name = "created", nullable = false, insertable = false)
    private LocalDateTime created;

    /**
     * Column TextMessage which represent user's ext-message
     */
    @Column(name = "user_post")
    private String textMessage;

    @Column(name = "title")
    private String title;

    /**
     * Column Category reffered on Category enum with post's categories
     * Auto convert by CategoryConverter
     */
    @Column(nullable = false, updatable = true, insertable = true)
    private Category category;

    /**
     * Default Constructor
     */
    public Post() {
    }

    /**
     *
     * @param user - referenced to User Entity
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


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                //", created=" + created +
                ", title=" + title +
                ", textMessage='" + textMessage + '\'' +
                ", category=" + category +
                '}';
    }
}
