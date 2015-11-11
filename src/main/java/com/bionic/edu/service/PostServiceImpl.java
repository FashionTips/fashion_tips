package com.bionic.edu.service;

import com.bionic.edu.dao.PostDao;
import com.bionic.edu.entity.Post;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by maxim on 11/11/15.
 */
@Named
@Transactional
public class PostServiceImpl implements PostService {
    @Inject
    private PostDao postDao;

    @Override
    public Post getById(long id) {
        return postDao.getById(id);
    }

    @Override
    public List<Post> getAll() {
        return postDao.getAll();
    }

    @Override
    public Post save(Post post) {
        return postDao.save(post);
    }

    @Override
    public void update(Post post) {
        postDao.update(post);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Post> getUserPosts(long userId) {
        return null;
    }
}
