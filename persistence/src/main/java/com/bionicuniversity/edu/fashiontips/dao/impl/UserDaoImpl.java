package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * UserDao implementation.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao{

    @Override
    public User getByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery("User.getByLogin", User.class);
        try {
            return query.setParameter("login", login).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        try {
            return query.setParameter("email", email).getSingleResult();
        } catch (NoResultException ex) {
            return null;    // user does not exist in db, return null
        }
    }
}