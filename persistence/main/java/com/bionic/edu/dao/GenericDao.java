package com.bionic.edu.dao;

import com.bionic.edu.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Dao
 * Created by maxim on 11/18/15.
 */
public interface GenericDao<T extends BaseEntity, PK extends Serializable> {
    T save(T object);
    T getById(PK id);
    List<T> getAll();
    void delete(PK id);
}
