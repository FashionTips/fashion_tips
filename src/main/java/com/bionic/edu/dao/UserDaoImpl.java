package com.bionic.edu.dao;

import com.bionic.edu.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of UserDao interface.
 * Created by maxim on 11/5/15.
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User add(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public void delete(long id) {
        em.remove(getById(id));
    }

    @Override
    public User getById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) em.createQuery("SELECT a FROM account a").getResultList();
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }
}
