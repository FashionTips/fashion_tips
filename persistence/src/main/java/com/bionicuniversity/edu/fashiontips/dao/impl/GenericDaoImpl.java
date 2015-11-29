package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.GenericDao;
import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * GenericDao implementation.
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(PK id) {
        em.remove(getById(id));
    }

    @Override
    @Transactional
    public void deleteAll() {
        em.createQuery(String.format("DELETE FROM %s", templateClass.getSimpleName())).executeUpdate();
    }
}
