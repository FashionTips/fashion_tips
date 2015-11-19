package com.bionic.edu.service.impl;

import com.bionic.edu.dao.GenericDao;
import com.bionic.edu.entity.BaseEntity;
import com.bionic.edu.service.GenericService;
import com.bionic.edu.service.util.exception.NotFoundException;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Sergiy on 18.11.2015
 * Project: fashion-tips
 */
public class GenericServiceImpl<T extends BaseEntity<PK>, PK extends Serializable> implements GenericService<T, PK> {
    @Inject
    protected GenericDao<T, PK> repository;

    private Class<T> tClass;

    GenericServiceImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        tClass = (Class) type.getActualTypeArguments()[0];
    }
//    throw new NotFoundException(
//            String.format("Could not found %s with id  = %d",
//            tClass.getSimpleName().toLowerCase(), id));

    public T save(T object) {
        return repository.save(object);
    }

    public void update(T object) throws NotFoundException {
        repository.save(object);
    }

    public void delete(PK id) throws NotFoundException {
        repository.delete(id);
    }

    public T get(PK id) throws NotFoundException {
        T object = repository.getById(id);
        if (object == null){
            throw new NotFoundException(
            String.format("Could not found %s with id  = %d",
            tClass.getSimpleName().toLowerCase(), id));
        }
        return object;
    }

    public List<T> getAll() {
        return repository.getAll();
    }
}
