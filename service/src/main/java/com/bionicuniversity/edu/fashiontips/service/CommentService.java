package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service to deal with user's comments.
 *
 * @author Maksym Dolia
 * @since 13/12/2015
 */
public interface CommentService {

    /**
     * Links comment to post by given id, and saves to persistence.
     *
     * @param comment comment to save
     * @param postId  post's id
     * @return saved comment
     */
    @Transactional
    Comment save(Comment comment, long postId);

    /**
     * Returns all comments of post (by given post id).
     *
     * @param postId post's id
     * @return list of comments
     */
    @Transactional
    List<Comment> findAllByPostId(Long postId);
}
