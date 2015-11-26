package com.bionicuniversity.edu.fashiontips.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by slav9nin on 25.11.2015.
 */
@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "CREATED", nullable = true, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created; //LocaleDateTime.now();

    @Column(name = "USER_POST", insertable = true, updatable = true, nullable = true, length = 256)
    private String textMessage;

    public Post() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        if (!super.equals(o)) return false;

        Post post = (Post) o;

        if (!user.equals(post.user)) return false;
        if (!created.equals(post.created)) return false;
        return textMessage.equals(post.textMessage);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + textMessage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + super.getId() + '\'' +
                "user=" + user +
                ", created=" + created +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }
}
