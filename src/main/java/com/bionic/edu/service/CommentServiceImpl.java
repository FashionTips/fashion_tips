package com.bionic.edu.service;

import com.bionic.edu.LoggerWrapper;
import com.bionic.edu.model.Comment;
import com.bionic.edu.repository.CommentRepository;
import com.bionic.edu.util.exception.NotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */
@Named
public class CommentServiceImpl implements CommentService {

    private static final LoggerWrapper LOG = LoggerWrapper.get(CommentServiceImpl.class);

    @Inject
    private CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment, int userId) {
        return commentRepository.save(comment, userId);
    }

    @Override
    public void update(Comment comment, int userId) throws NotFoundException {
        if (commentRepository.save(comment, userId) == null)
            throw LOG.getNotFoundException(String.format("Could not update comment with id=%d and userId=%d",comment.getId(), userId));
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!commentRepository.delete(id, userId))
            throw LOG.getNotFoundException(String.format("Could not delete comment with id=%d and userId=%d", id, userId));
    }

    @Override
    public Comment get(int id) throws NotFoundException {
        Comment comment = commentRepository.get(id);
        if (comment == null)
            throw LOG.getNotFoundException(String.format("Could not find comment with id=%d",id));
        return comment;
    }

    @Override
    public List<Comment> getAllByRequest(int requestId) {
        return commentRepository.getAllbyRequest(requestId);
    }
}
