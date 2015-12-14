package com.bionicuniversity.edu.fashiontips.service;


import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Service Interface.
 */
public interface GenericService<T extends BaseEntity<PK>, PK extends Serializable> {

    /**
     * Saves given entity to persistence.
     *
     * @param object entity to save
     * @return saved entity
     */
    T save(T object);

    /**
     * Updates stored entity with given data.
     *
     * @param object data
     */
    void update(T object);

    /**
     * Deletes entity from persistence layer with given id.
     *
     * @param id entity's id
     */
    void delete(PK id);

    /**
     * Returns entity with given id from persistence.
     *
     * @param id entity's id
     * @return retrieved entity
     */
    T get(PK id);

    /**
     * Returns all entities from persistence.
     *
     * @return entities
     */
    List<T> getAll();
}
