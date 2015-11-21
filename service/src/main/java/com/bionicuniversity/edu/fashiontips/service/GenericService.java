package com.bionicuniversity.edu.fashiontips.service;


import com.bionicuniversity.edu.fashiontips.entity.BaseEntity;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sergiy on 18.11.2015
 * Project: fashion-tips
 */
public interface GenericService<T extends BaseEntity<PK>, PK extends Serializable> {

    T save(T object);

    void update(T object) throws NotFoundException;

    void delete(PK id) throws NotFoundException;

    T get(PK id) throws NotFoundException;

    List<T> getAll();

}