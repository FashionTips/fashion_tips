package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA implementation of {@code TagDao} interface.
 *
 * @author Vadym Golub
 * @author Alexandr Laktionov
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {

    @Override
    public Tag findTag(String tagName) {
        TypedQuery<Tag> query =
                em.createQuery("SELECT t FROM Tag t WHERE t.value = :name", Tag.class);
        query.setParameter("name", tagName);
        return query.getSingleResult();
    }
}
