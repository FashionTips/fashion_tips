package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Tag;

/**
 * TagDao interface
 */
public interface TagDao extends GenericDao<Tag, Long> {
    Tag getByName(String name);
}
