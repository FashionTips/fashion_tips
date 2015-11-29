package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * UserDao implementation.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao{
    @Override
    public User getByLogin(String login) {
        TypedQuery<User> query = em.createQuery("SELECT u from User u WHERE u.login=:login", User.class);
        return query.setParameter("login", login).getSingleResult();
    }
}
