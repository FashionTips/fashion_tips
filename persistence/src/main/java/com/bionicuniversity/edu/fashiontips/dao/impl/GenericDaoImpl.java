package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.GenericDao;
import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(T obj) {
        em.remove(obj);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(PK id) {
        em.remove(getById(id));
    }

    @Override
    public T getReference(PK id) {
        return em.getReference(templateClass, id);
    }

    @Override
    public boolean exists(PK id) {
        TypedQuery<Long> query = em.createQuery(String.format("SELECT COUNT(e.id) FROM %s e WHERE e.id =:id", templateClass.getSimpleName()), Long.class);
        return query.setParameter("id", id).getSingleResult() > 0;
    }
}
