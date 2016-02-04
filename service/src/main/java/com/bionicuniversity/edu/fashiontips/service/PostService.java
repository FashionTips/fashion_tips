package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service to deal with user's posts.
 *
 * @author Sergiy
 * @author Maksym Dolia
 * @author Volodymyr Portianko
 * @since 25/11/2015
 */
public interface PostService {

    Optional<Post> get(long id);

    /**
     * Returns entity with given id from persistence.
     *
     * @param id entity's id
     * @param loggedUser entity of logged user
     * @return retrieved entity
     */
    Optional<Post> get(long id, User loggedUser);

    /**
     * Returns all user's posts.
     *
     * @param user user
     * @return list of posts
     */
    List<Post> findAllByUser(User user, User loggedUser);
    List<Post> findAllByHashTag(String hashTag, User loggedUser);
    List<Post> findAllByCategory(Post.Category category, User loggedUser);

    /**
     * Returns ist of posts associated with given tag
     *
     * @return {@code List<Post>}
     *
     * @throws NotFoundException  if the tag does
     * not exists
     */
    List<Post> findAllByTagAndTagTypeValue(String tag, String tagType, User loggedUser);
    List<Post> findAllByTagTypeValue(String tagType, User loggedUser);
    List<Post> findAllByClothes(String name, User loggedUser);
    List<Post> findAll(User loggedUser);

    /**
     * Handles requests for changing "like" status of post.
     *
     * @param id post id
     * @param loggedUser logged user entity
     **/
    void toggleLikedStatus(long id, User loggedUser);

    @PreAuthorize("#post.user.login == authentication.name")
    void update(Post update, Post post);

    /**
     * Deletes given post from persistence.
     *
     * @param id post to delete
     * @param loggedUser
     */
    void delete(long id, User loggedUser);

    @Transactional
    Post save(Post post);
}
