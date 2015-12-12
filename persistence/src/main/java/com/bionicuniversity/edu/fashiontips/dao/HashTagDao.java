package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.HashTag;

/**
 * HashTagDao interface
 */
public interface HashTagDao extends GenericDao<HashTag, Long> {
    HashTag getByName(String name);
}