package com.bionicuniversity.edu.fashiontips.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class for retrieve comments from DB
 *
 * @author alaktionov aka slav9nin
 */

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity<Long> {

    /**
     * Empty constructor
     */
    public Comment() {}

    /**
     *
     * @param text String which contains user's comment
     * @param post {@link Post} which referred on user's post
     */
    public Comment(final String text, final Post post) {
        this.text = text;
        this.post = post;
    }

    /**
     * @param post - Represent the root post which this comment attached
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * @param text - Represent user's message following up to {@link Post}
     */
    @Column(name = "text")
    @NotBlank
    private String text;

    /**
     * Column created which represent creating date of post
     * Auto convert by LocalDateTimeConverter
     * @param created - Represent time when this comment was created
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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
    public java.lang.String toString()
    {
        return "Comment{" +
                "id=" + getId() +
                ", post=" + post +
                ", text=" + text +
                ", created=" + created +
                '}';
    }
}
