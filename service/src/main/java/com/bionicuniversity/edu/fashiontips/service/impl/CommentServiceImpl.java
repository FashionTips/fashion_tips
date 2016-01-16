package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.service.CommentService;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of {@code CommentService} interface.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 13/12/2015
 */
@Named
public class CommentServiceImpl extends GenericServiceImpl<Comment, Long> implements CommentService {

    @Inject
    private CommentDao commentDao;

    @Inject
    private PostDao postDao;

    @Override
    public Comment save(Comment comment, long postId) {
        comment.setPost(postDao.getReference(postId));
        comment.setCreated(LocalDateTime.now());
        return save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentDao.findAllByPost(postDao.getReference(postId));
    }
}
