package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * UserDao implementation.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao{
    @Override
    public User getByLogin(String login) {
        TypedQuery<User> query = em.createQuery("SELECT u from User u WHERE u.login=:login", User.class);
        List<User> results = query.setParameter("login", login).getResultList();

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
