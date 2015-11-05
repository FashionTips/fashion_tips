package com.bionic.edu.crud;

import com.bionic.edu.entity.Usr;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of UserDao interface.
 * Created by maxim on 11/5/15.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Usr create(Usr user) {
        em.persist(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(long id) {
        em.remove(read(id));
    }

    @Override
    @Transactional
    public Usr read(long id) {
        return em.find(Usr.class, id);
    }

    @Override
    @Transactional
    public void update(Usr user) {
        em.merge(user);
    }
}
