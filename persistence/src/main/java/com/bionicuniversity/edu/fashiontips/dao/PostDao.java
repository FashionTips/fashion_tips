package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.List;

/**
 * PostDao interface
 */
public interface PostDao extends GenericDao<Post, Long> {

    /**
     * Returns all user's posts from database.
     *
     * @param user user
     * @return list of posts
     */
    List<Post> findByUser(User user);

    /**
     * Returns all posts with hashTag, sorted by time created.
     *
     * @param word
     * @return list of posts which contains word
     */
    List<Post> findByWord(String word);

    /**
     * Returns all posts with category, sorted by time created.
     *
     * @param categoryName
     * @return list of posts
     */
    List<Post> findByCategory(String categoryName);
    /**
     * Returns all posts sorted by time created.
     * @return list of all posts
     */
    List<Post> findAll();
}
