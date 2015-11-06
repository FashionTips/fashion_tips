package com.bionic.edu.service;

import com.bionic.edu.model.User;
import com.bionic.edu.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
public class UserServiceImpl implements UserService {


    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void update(User user) throws NotFoundException {

    }

    @Override
    public void delete(int id) throws NotFoundException {

    }

    @Override
    public User get(int id) throws NotFoundException {
        return null;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
