package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;

import java.util.List;

/**
 * DAO interface to deal with {@code Tag} entity.
 *
 * @author Vadym Golub
 * @author Alexandr Laktionov
 */
public interface TagDao extends GenericDao<Tag, Long> {

    Tag findTag(String tagName);
    List<TagLine> findTagLinesByTag(Tag tag);

}
