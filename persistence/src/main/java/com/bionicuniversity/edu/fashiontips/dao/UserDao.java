package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.User;

/**
 * User Dao interface.
 */
public interface UserDao extends GenericDao<User, Long> {

    User getByLogin(String login);
}
