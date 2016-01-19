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
        comment.setAvailable(true);
        comment.setPost(postDao.getReference(postId));
        comment.setCreated(LocalDateTime.now());
        return save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentDao.findAllByPost(postDao.getReference(postId));
    }

    @Override
    public void hideById(long commentId, String login) {
        Comment comment = commentDao.getById(commentId);
        if(comment != null) {
            if(comment.getUser().getLogin().equals(login)){
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
