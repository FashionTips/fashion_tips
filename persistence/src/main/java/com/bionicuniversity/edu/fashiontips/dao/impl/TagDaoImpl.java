package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * JPA implementation of {@code TagDao} interface.
 *
 * @author Vadym Golub
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {
    @Override
    public boolean existsValue(String value) {
        TypedQuery<Long> query = em.createNamedQuery("Tag.existsValue", Long.class);
        return query.setParameter("tagValue", value).getSingleResult() > 0;
    }
}