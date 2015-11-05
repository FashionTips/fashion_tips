package com.bionic.edu.crud;

import com.bionic.edu.entity.Usr;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by maxim on 11/5/15.
 */
public class UserService {

    public EntityManager em = Persistence.createEntityManagerFactory("fashion_tips").createEntityManager();

    public Usr add(Usr usr) {
        em.getTransaction().begin();
        Usr usrFromDB = em.merge(usr);
        em.getTransaction().commit();
        return usrFromDB;
    }

    public void delete(long id) {
        em.getTransaction();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Usr get(long id) {
        em.getTransaction();
        return em.find(Usr.class, id);
    }

    public void update(Usr usr) {
        em.getTransaction();
        em.merge(usr);
        em.getTransaction().commit();
    }
}
