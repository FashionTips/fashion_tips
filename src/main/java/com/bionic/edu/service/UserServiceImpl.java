package com.bionic.edu.service;

import com.bionic.edu.dao.UserDao;
import com.bionic.edu.entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by maxim on 11/5/15.
 */
@Named
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
