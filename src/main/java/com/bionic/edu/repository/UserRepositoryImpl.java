package com.bionic.edu.repository;

import com.bionic.edu.model.User;

import java.util.List;

/**
 * Created by VPortianko on 06.11.2015.
 */
public class UserRepositoryImpl implements  UserRepository {


    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
