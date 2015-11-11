package com.bionic.edu.dao;

import com.bionic.edu.entity.User;

import java.util.List;

/**
 * Interface for UserDao.
 * Created by maxim on 11/5/15.
 */
public interface UserDao {

    User save(User user);
    User getById(long id);
    List<User> getAll();
    void update(User user);
    void delete(long id);
}
