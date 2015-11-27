package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.util.CategoryConverter;
import com.bionicuniversity.edu.fashiontips.util.LocalDateTimeConverter;

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
     */
    @Column(name = "CREATED", nullable = false, insertable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime created;

    /**
     * Column TextMessage which represent user's ext-message
     */
    @Column(name = "USER_POST", insertable = true, updatable = true, nullable = true, length = 256)
    private String textMessage;

    /**
     * Column Category reffered on Category enum with post's categories
     */
    @Column(nullable = false, updatable = true, insertable = true)
    @Convert(converter = CategoryConverter.class)
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
    public Post(User user, String message, Category category) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        if (!super.equals(o)) return false;

        Post post = (Post) o;

//        if (!user.equals(post.user)) return false;
//        if (category != post.category) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "user=" + user +
                //", created=" + created +
                ", textMessage='" + textMessage + '\'' +
                ", category=" + category +
                '}';
    }
}
