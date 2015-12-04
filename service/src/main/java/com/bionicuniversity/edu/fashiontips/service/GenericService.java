package com.bionicuniversity.edu.fashiontips.service;


import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 *  Generic Service Interface.
 */
public interface GenericService<T extends BaseEntity<PK>, PK extends Serializable> {

    T save(T object);

    void update(T object);

    void delete(PK id);

    T get(PK id);

    List<T> getAll();
}
