package com.bionic.edu.service;

import com.bionic.edu.model.User;
import com.bionic.edu.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
public interface UserService {

    User save(User user);

    void update(User user) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();
}
