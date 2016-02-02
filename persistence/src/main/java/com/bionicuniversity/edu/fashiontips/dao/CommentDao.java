package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;

import java.util.List;

/**
 * DAO to deal with {@code Comment} entity in database.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 8/12/2015
 */
public interface CommentDao extends GenericDao<Comment, Long> {

    /**
     * Retrieves and returns all comments of given post.
     *
     * @param post post
     * @return list of comments
     */
    List<Comment> findAllByPost(Post post);
}
