package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.List;

/**
 * Service to deal with user's posts.
 *
 * @author Sergiy
 * @author Maksym Dolia
 * @author Volodymyr Portianko
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
    List<Post> findAllByUser(User user, User loggedUser);
    List<Post> findAllByHashTag(String hashTag, User loggedUser);
    List<Post> findAll(User loggedUser);

    /**
     * Handles requests for changing "like" status of post.
     *
     * @param id post id
     * @param loggedUser logged user entity
     **/
    void toggleLikedStatus(Long id, User loggedUser);
}
