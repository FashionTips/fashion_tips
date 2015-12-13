package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

/**
 * Created by slav9nin on 09.12.2015.
 */
public class CommentTestData {

    public static final User user1 = new User("login1", "email1@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User user2 = new User("login2", "email2@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User user3 = new User("login3", "email3@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User user4 = new User("login4", "email4@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");


    public static final Post post1 = new Post(user1, "title1", "what fits me with these pants?", Post.Category.QUESTION);
    public static final Post post2 = new Post(user2, "title2", "what hat does put on", Post.Category.QUESTION);
    public static final Post post3 = new Post(user3, "title3", "red is cool?", Post.Category.QUESTION);
    public static final Post post4 = new Post(user4, "title4", "cool?", Post.Category.QUESTION);

    public static final Comment comment1 = new Comment("cool!", post1, user1);
    public static final Comment comment2 = new Comment("amazing!!", post2, user1);
    public static final Comment comment3= new Comment("perfect!!!", post3, user2);
    public static final Comment comment4= new Comment("perfect!!!", post3, user2);

    static {
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        user4.setId(4L);

        post1.setId(1L);
        post2.setId(2L);
        post3.setId(3L);
        post4.setId(4L);

        comment1.setId(1L);
        comment2.setId(2L);
        comment3. setId(3L);
    }
}
