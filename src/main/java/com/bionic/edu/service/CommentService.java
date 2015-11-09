package com.bionic.edu.service;

import com.bionic.edu.model.Comment;
import com.bionic.edu.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */
public interface CommentService {

    Comment save(Comment comment, int userId);

    void update(Comment comment, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Comment get(int id) throws NotFoundException;

    List<Comment> getAllByRequest(int requestId);
}
