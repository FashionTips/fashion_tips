package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotAllowedActionException;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
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

    @Override
    public Comment save(Comment comment, long postId) {

        if(!postDao.exists(postId)) throw new NotFoundException(String.format("Post with id '%d' was not found.", postId));

        Post post = postDao.getById(postId);

        if(!post.isCommentsAllowed()) {
            throw new NotAllowedActionException(String.format("Post Id '%d': author prohibited to comment.", postId));
        }

        comment.setPost(post);
        comment.setCreated(LocalDateTime.now());
        return commentDao.save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {

        Objects.requireNonNull(postId, "The post id cannot be null");
        return commentDao.findAllByPost(postDao.getReference(postId));
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
}
