package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * TagDao implementation
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {

    /**
     * Returns the Tag entity with name = "name" from DB. If it not exist, returned null.
     *
     * @param name the name of Tag
     * @return the value of the "name" for the required Tag
     */
    @Override
    public Tag getByName(String name) {

        TypedQuery<Tag> query = em.createQuery("SELECT t from Tag t WHERE t.name=:name", Tag.class);
        List<Tag> results = query.setParameter("name", name).getResultList();

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
