package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Entity Class Post which mapped on post table in DB
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
    @JsonIgnoreProperties(value = {"id", "email"})
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
    @Column(name = "user_post", length = 1000)
    private String textMessage;

    @NotBlank(message = "Title could not be empty.")
    @Size(max = 100, message = "Title may not has more than 100 characters.")
    @Column(name = "title", length = 100)
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
    @Fetch(FetchMode.SELECT)
    private List<Image> images;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> comments;

    /*
    * Set of users that liked post
    * */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_user_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @LazyCollection(LazyCollectionOption.EXTRA)
    @JsonIgnore
    private Set<User> likedByUsers;

    /*
    * Sum of users that liked post
    * Used for transferring amount of likes to clients
    * */
    @Transient
    private Long likes;

    /*
    * Flag that shows whether the post is liked by authenticated user
    * Used for transferring status of post for current logged user
     * */
    @Transient
    private Boolean isLikedByAuthUser;

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Boolean getIsLikedByAuthUser() {
        return isLikedByAuthUser;
    }

    public void setIsLikedByAuthUser(Boolean isLikedByAuthUser) {
        this.isLikedByAuthUser = isLikedByAuthUser;
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
                ", likes=" + likes +
                ", isLikedByAuthUser=" + isLikedByAuthUser +
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
