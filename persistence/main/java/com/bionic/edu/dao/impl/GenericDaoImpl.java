package com.bionic.edu.dao.impl;

import com.bionic.edu.dao.GenericDao;
import com.bionic.edu.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * GenericDao implementation.
 * Created by maxim on 11/18/15.
 */
public abstract class GenericDaoImpl<T extends BaseEntity, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> templateClass;

    public GenericDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        templateClass = (Class) type.getActualTypeArguments()[0];
    }

    @Override
    public T save(T object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
        if (object.getId() == null) {
            em.persist(object);
            return object;
        }
        return em.merge(object);
    }

    @Override
    public T getById(PK id) {
        return em.find(templateClass, id);
    }

    @Override
    public List<T> getAll() {
        return em.createQuery(String.format(
                        "SELECT o FROM %s o", templateClass.getSimpleName()),
                templateClass).getResultList();
    }

    @Override
    public void delete(PK id) {
        em.remove(getById(id));
    }
}
