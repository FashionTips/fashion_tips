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
     * Returns all author's posts including hidden
     *
     * @param author
     * @return list of posts
     */
    List<Post> findForAuthor(User author);

    /**
     * Returns all posts with hashTag, sorted by time created.
     *
     * @param word
     * @return list of posts which contains word
     */
    List<Post> findByWord(String word);

    /**
     * Returns all posts with hashTag, sorted by time created.
     *
     * @param category category to search
     * @return list of posts
     */
    List<Post> findByCategory(Post.Category category);


    /**
     * Returns all posts sorted by time created.
     *
     * @return list of all posts
     */
    List<Post> findAll();

    List<Post> findUnpublished();

    /**
     * Returns all PUBLISHED posts with appropriate {@code Tag & TagType}, sorted by time created.
     * @see com.bionicuniversity.edu.fashiontips.entity.Post.Status
     * @param value {@code Tag}'s value
     * @param tagTypeId {@code TagType}'s id
     * @return list of posts
     */
    List<Post> findByTagValueAndTagTypeId(String value,  Long tagTypeId);

    /**
     * Returns all PUBLISHED posts with appropriate {@code TagType}, sorted by time created.
     * @param tagTypeId {@code TagType}' id
     * @return list of posts
     * @see com.bionicuniversity.edu.fashiontips.entity.Post.Status
     */
    List<Post> findByTagTypeId(Long tagTypeId);

    /**
     * Returns all PUBLISHED posts with appropriate type of {@code Clothes}, sorted by time created.
     * @param clothesId {@code Clothes}'s id
     * @return {@code List<Post>}
     * @see com.bionicuniversity.edu.fashiontips.entity.Post.Status
     */
    List<Post> findByClothesId(Long clothesId);
}
