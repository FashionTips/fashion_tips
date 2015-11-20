package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

/**
 * UserDao implementation.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao{
}
