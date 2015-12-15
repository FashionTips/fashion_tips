package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.List;

/**
 * Service to deal with user's posts.
 *
 * @author Sergiy
 * @author Maksym Dolia
 * @since 25/11/2015
 */
public interface PostService extends GenericService<Post, Long> {

    /**
     * Returns all user's posts.
     *
     * @param user user
     * @return list of posts
     */
    List<Post> findByUser(User user);
    List<Post> findByHashTag(String hashTag);
    List<Post> findAll();
}
