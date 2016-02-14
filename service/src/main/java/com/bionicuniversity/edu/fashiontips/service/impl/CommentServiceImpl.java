package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.EmailService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotAllowedActionException;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

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

    @Inject
    private EmailService emailService;

    @Override
    public Comment save(Comment comment, long postId) {

        if (!postDao.exists(postId))
            throw new NotFoundException(String.format("Post with id '%d' was not found.", postId));
        Post post = postDao.getReference(postId);
        if (!post.isCommentsAllowed()) {
            throw new NotAllowedActionException(String.format("Post Id '%d': author prohibited to comment.", postId));
        }
        comment.setAvailable(true);
        comment.setPost(post);
        comment.setCreated(LocalDateTime.now());

        comment = commentDao.save(comment);

        //Create email notification about new comment
        emailService.createNotificationAboutNewComment(post, comment.getUser());
        return comment;
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {

        Objects.requireNonNull(postId, "The post id cannot be null");
        commentDao.findAllByPost(postDao.getReference(postId));
        List<Comment> comments = commentDao.findAllByPost(postDao.getReference(postId));
        for (Comment c : comments) {
            if (!c.isAvailable()) {
                c.setText("");
            }
        }
        return comments;
    }

    @Override
    @PreAuthorize("#post.user.login == authentication.name")
    public boolean block(@P("post") Post post) {
        Objects.requireNonNull(post, "Post cannot be null.");
        boolean blockedStatus = post.isCommentsAllowed();
        post.setCommentsAllowed(!blockedStatus);
        post = postDao.save(post);
        return post.isCommentsAllowed();
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

    @Override
    public Comment update(Comment comment, String login) {
        if (!commentDao.exists(comment.getId()))
            throw new NotFoundException(String.format("Comment not found by id = %d", comment.getId()));
        Comment presentComment = commentDao.getById(comment.getId());
        if (!presentComment.getUser().getLogin().equals(login))
            throw new AccessDeniedException(String.format("Comment doesn't belong to user with login = %s", login));
        comment.setCreated(presentComment.getCreated());
        comment.setUser(presentComment.getUser());
        comment.setPost(presentComment.getPost());
        return commentDao.save(comment);
    }
}
