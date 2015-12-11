package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;

import javax.inject.Named;

/**
 * User Service Implementation.
 */
@Named
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

    @Override
    public User getByLogin(String login) {
        User user = ((UserDao) repository).getByLogin(login);
        if (user == null) throw new NotFoundException(String.format(
                "User with login %s is not found.", login));
        return user;
    }
}
