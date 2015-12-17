package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Dao Interface.
 */
public interface GenericDao<T extends BaseEntity, PK extends Serializable> {
    T save(T object);

    T getById(PK id);

    List<T> getAll();

    void delete(PK id);

    /**
     * Deletes all records.
     */
    void deleteAll();

    T getReference(PK id);
}
