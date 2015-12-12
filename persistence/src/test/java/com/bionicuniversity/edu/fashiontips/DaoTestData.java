package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Sergiy on 06.12.2015
 * Project: fashion-tips
 */
public final class DaoTestData {
    /*existing users*/
    public static final User USER1 = new User(1L, "login1", "email1@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User USER2 = new User(2L, "login2", "email2@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User USER3 = new User(3L, "login3", "email3@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    /*new user*/
    public static final User USER4 = new User(4L, "login4", "email4@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");

    public static final List<User> USERS = Arrays.asList(USER1, USER2, USER3);

    /*existing tags*/
    public static final HashTag HASHTAG1 = new HashTag(1L, "#hashtag1");
    public static final HashTag HASHTAG2 = new HashTag(2L, "#hashtag2");
    public static final HashTag HASHTAG3 = new HashTag(3L, "#hashtag3");

    /* Default images, which stored in ${application.images.path} */
    public static final Image IMAGE1 = new Image(1L,"1-1kurtka.jpg");
    public static final Image IMAGE2 = new Image(2L,"2-13235_huge_weman_shoes_0.jpg");
    public static final Image IMAGE3 = new Image(3L,"3-jeans1.jpg");
    public static final Image IMAGE4 = new Image(4L,"4-jeans2.jpg");
    public static final Image IMAGE5 = new Image(5L,"5-kurtka.jpg");
    public static final Image IMAGE6 = new Image(6L,"6-sapogi.jpeg");

    /* Other images for testing */
    public static final String TEST_IMAGE_DIRECTORY = "../test_images/";

    public static final Image IMAGE_NEW = new Image(null, "newImage.jpg");

    /*existing posts*/
    public static final Post POST1 = new Post(1L, USER1, "title1", "#hashtag1 what #hashtag2 fits me with these pants #hashtag3?", Post.Category.QUESTION, new HashSet<>(Arrays.asList(HASHTAG1, HASHTAG2, HASHTAG3)), new HashSet<>(Arrays.asList(IMAGE1, IMAGE2, IMAGE3)));
    public static final Post POST2 = new Post(2L, USER2, "title2", "what hat #hashtag1 does put on #hashtag2?", Post.Category.QUESTION, new HashSet<>(Arrays.asList(HASHTAG1, HASHTAG2)), new HashSet<>(Arrays.asList(IMAGE4, IMAGE5)));
    public static final Post POST3 = new Post(3L, USER3, "title3", "red is cool #hashtag1?", Post.Category.QUESTION, new HashSet<>(Arrays.asList(HASHTAG1)), new HashSet<>(Collections.singletonList(IMAGE6)));
    public static final Post POST4 = new Post(4L, USER1, "title1", "what fits me with these pants? Again", Post.Category.POST, new HashSet<>(), new HashSet<>());
    public static final Post POST5 = new Post(5L, USER2, "title2", "what hat does put on? Again", Post.Category.POST, new HashSet<>(), new HashSet<>());
    public static final Post POST6 = new Post(6L, USER3, "title3", "red is cool? Again", Post.Category.POST, new HashSet<>(), new HashSet<>());
    /*new post*/
    public static final Post POST7 = new Post(7L, USER1, "title4", "How my glasses fits me?", Post.Category.POST, new HashSet<>(), new HashSet<>(Arrays.asList(IMAGE4, IMAGE5)));

    public static final List<Post> POSTS = Arrays.asList(POST1, POST2, POST3, POST4, POST5, POST6);

}