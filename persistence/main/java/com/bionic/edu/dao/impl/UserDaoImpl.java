package com.bionic.edu.dao.impl;

import com.bionic.edu.dao.UserDao;
import com.bionic.edu.entity.User;
import org.springframework.stereotype.Repository;

/**
 * UserDao implementation.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao{
}
