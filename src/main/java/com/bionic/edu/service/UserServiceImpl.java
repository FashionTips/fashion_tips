package com.bionic.edu.service;

import com.bionic.edu.dao.UserDao;
import com.bionic.edu.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by maxim on 11/5/15.
 */
@Named
@Transactional
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(user);
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
