package com.bionic.edu.repository;

import com.bionic.edu.model.Comment;

import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */
public interface CommentRepository {

    //null if updated comment do not belong to user or not found
    Comment save(Comment comment, int userId);

    //fasle if comment do not belong to user or not found
    boolean delete(int id, int userId);

    //null if not found
    Comment get(int id);

    List<Comment> getAllbyRequest(int requestId);
}
