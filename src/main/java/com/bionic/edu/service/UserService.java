package com.bionic.edu.service;

import com.bionic.edu.entity.User;

import java.util.List;

/**
 * Created by maxim on 11/5/15.
 */
public interface UserService {
    User save(User user);
    void delete(long id);
    User getById(long id);
    List<User> getAll();
    void update(User user);
}
