package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.entity.util.AuthorDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.AuthorSerializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeDeserializer;
import com.bionicuniversity.edu.fashiontips.entity.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity class, which represents post's comment.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 8/12/2015
 */

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity<Long> {

    /**
     * Empty constructor for JPA purposes.
     */
    public Comment() {
    }

    /**
     * Constructs comment with given parameters.
     *
     * @param text comment's body
     * @param post linked post
     * @param user author
     */
    public Comment(String text, Post post, User user) {
        this.text = text;
        this.post = post;
        this.user = user;
    }

    public Comment(Long id, String text, Post post, User user) {
        this(id, text, post, user, LocalDateTime.now());
    }

    public Comment(Long id, String text, Post post, User user, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.post = post;
        this.user = user;
        this.created = created;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @JsonProperty("author")
    @JsonSerialize(using = AuthorSerializer.class)
    @JsonDeserialize(using = AuthorDeserializer.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "text")
    @NotBlank
    @Size(max = 255, message = "Comment body may not has more than 255 characters.")
    private String text;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created", nullable = false, insertable = false)
    private LocalDateTime created;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user='" + user.getLogin() + '\'' +
                ", created='" + created + '\'' +
                '}';
    }

}