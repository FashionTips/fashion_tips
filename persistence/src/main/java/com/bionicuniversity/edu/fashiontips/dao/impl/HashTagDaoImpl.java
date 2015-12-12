package com.bionicuniversity.edu.fashiontips.dao.impl;


import com.bionicuniversity.edu.fashiontips.dao.HashTagDao;
import com.bionicuniversity.edu.fashiontips.entity.HashTag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * HashTagDao implementation
 */
@Repository
public class HashTagDaoImpl extends GenericDaoImpl<HashTag, Long> implements HashTagDao {
    @Override
    public HashTag getByName(String name) {
        TypedQuery<HashTag> query = em.createQuery("SELECT ht from HashTag ht WHERE ht.name=:name", HashTag.class);
        List<HashTag> results = query.setParameter("name", name).getResultList();

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}