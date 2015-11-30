package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.GenericDao;
import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;
import com.bionicuniversity.edu.fashiontips.service.GenericService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Generic Service Implementation.
 */
public class GenericServiceImpl<T extends BaseEntity<PK>, PK extends Serializable> implements GenericService<T, PK> {
    @Inject
    protected GenericDao<T, PK> repository;

    private Class<T> tClass;

    GenericServiceImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        tClass = (Class) type.getActualTypeArguments()[0];
    }

    public T save(T object) {
        return repository.save(object);
    }

    public void update(T object) {
        validate(object.getId());
        repository.save(object);
    }

    public void delete(PK id) {
        validate(id);
        repository.delete(id);
    }

    public T get(PK id) {
        validate(id);
        return repository.getById(id);
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    /* check whether object with given id exists in persistence and throws NotFoundException in a case of absence */
    private void validate(PK id) {
        T object = repository.getById(id);
        if (object == null) {
            throw new NotFoundException(
                    String.format("Could not found %s with id  = %s",
                            tClass.getSimpleName().toLowerCase(), id.toString()));
        }
    }
}
