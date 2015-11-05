package com.bionic.edu.crud;

import com.bionic.edu.entity.Usr;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by maxim on 11/5/15.
 */
@Named
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public Usr add(Usr user) {
        return userDao.create(user);
    }

    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public Usr get(long id) {
        return userDao.read(id);
    }

    @Override
    public void update(Usr user) {
        userDao.update(user);
    }
}
