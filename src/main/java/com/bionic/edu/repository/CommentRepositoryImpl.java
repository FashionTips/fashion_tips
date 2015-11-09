package com.bionic.edu.repository;

import com.bionic.edu.model.Comment;
import com.bionic.edu.repository.datajpa.ProxyCommentRepository;
import com.bionic.edu.repository.datajpa.ProxyUserRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by VPortianko on 09.11.2015.
 */
@Named
public class CommentRepositoryImpl implements CommentRepository {

    @Inject
    private ProxyCommentRepository proxy;

    @Inject
    private ProxyUserRepository userProxy;

    @Override
    @Transactional
    public Comment save(Comment comment, int userId) {
        if (!comment.isNew() && get(comment.getId()).getUser().getId() != userId) {
            return null;
        }
        comment.setUser(userProxy.getOne(userId));
        return proxy.save(comment);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id,userId) != 0;
    }

    @Override
    public Comment get(int id) {
        return proxy.get(id);
    }

    @Override
    public List<Comment> getAllbyRequest(int requestId) {
        return proxy.getAllByRequest(requestId);
    }
}
