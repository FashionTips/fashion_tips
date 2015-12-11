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
    List<Post> getAllByUser(User user);
}
