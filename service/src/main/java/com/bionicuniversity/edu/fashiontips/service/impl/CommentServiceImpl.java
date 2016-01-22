package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@code CommentService} interface.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 13/12/2015
 */
@Named
public class CommentServiceImpl implements CommentService {

    @Inject
    private CommentDao commentDao;

    @Inject
    private PostDao postDao;

    @Override
    public Comment save(Comment comment, long postId) {
        if(!postDao.exists(postId)) throw new NotFoundException(String.format("Post with id '%d' was not found.", postId));
        comment.setAvailable(true);
        comment.setPost(postDao.getReference(postId));
        comment.setCreated(LocalDateTime.now());
        return commentDao.save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        Objects.requireNonNull(postId, "The post id cannot be null");
        List<Comment> comments = commentDao.findAllByPost(postDao.getReference(postId));
        for (Comment c : comments) {
            if (!c.isAvailable()){
                c.setText("");
            }
        }
        return comments;
    }

    @Override
    public void hideById(long commentId, String login) {
        Comment comment = commentDao.getById(commentId);
        if (comment != null) {
            if (comment.getUser().getLogin().equals(login)) {
                comment.setAvailable(false);
                commentDao.save(comment);
            } else {
                throw new AccessDeniedException(String.format("Comment doesn't belong to user with login = %s", login));
            }
        } else {
            throw new NotFoundException(String.format("Comment not found by id = %d", commentId));
        }
    }
}
