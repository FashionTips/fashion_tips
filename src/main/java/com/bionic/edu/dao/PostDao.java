package com.bionic.edu.dao;

import com.bionic.edu.entity.Post;

import java.util.List;

/**
 * Interface for PostDao.
 * Created by maxim on 11/6/15.
 */
public interface PostDao {
    Post getById(long id);
    List<Post> getAll();
    Post save(Post post);
    void update(Post post);
    void delete(long id);
}
