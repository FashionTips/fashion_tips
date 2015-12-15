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
     * Returns entity with given id from persistence.
     *
     * @param id entity's id
     * @param loggedUser entity of logged user
     * @return retrieved entity
     */
    Post get(Long id, User loggedUser);

    /**
     * Returns all user's posts.
     *
     * @param user user
     * @return list of posts
     */
    List<Post> getAllByUser(User user);

    void toggleLike(Long id, User loggedUser);
}
