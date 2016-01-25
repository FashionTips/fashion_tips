package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.entity.util.AuthorDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.AuthorSerializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
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
    @JsonSerialize(using = AuthorSerializer.class)
    @JsonDeserialize(using = AuthorDeserializer.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Column created which represent creating date of post
     * Auto convert by LocalDateTimeConverter
     */

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "post_images",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "img_id")
    )
    @Fetch(FetchMode.SELECT)
    @NotEmpty(message = "Image is required.")
    private List<Image> images;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> comments;

    @Column(name = "is_comments_allowed", nullable = false)
    private boolean commentsAllowed = true;

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

    public Post(Long id, User user, LocalDateTime created, String title, String textMessage, Category category, List<Image> images, List<Comment> comments, Set<User> likedByUsers, Long likes, Boolean isLikedByAuthUser) {
        this.id = id;
        this.user = user;
        this.created = created;
        this.textMessage = textMessage;
        this.title = title;
        this.category = category;
        this.images = images;
        this.comments = comments;
        this.likedByUsers = likedByUsers;
        this.likes = likes;
        this.isLikedByAuthUser = isLikedByAuthUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonIgnore
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

    public boolean isCommentsAllowed() {
        return commentsAllowed;
    }

    public void setCommentsAllowed(boolean commentsAllowed) {
        this.commentsAllowed = commentsAllowed;
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

    public Boolean getLikedByAuthUser() {
        return isLikedByAuthUser;
    }

    public void setLikedByAuthUser(Boolean likedByAuthUser) {
        isLikedByAuthUser = likedByAuthUser;
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
                ", comments=" + comments +
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
