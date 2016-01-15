package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Tag;

/**
 * DAO interface to deal with {@code Tag} entity.
 *
 * @author Vadym Golub
 */
public interface TagDao extends GenericDao<Tag, Long> {

    Tag findTag(String tagName);

}
