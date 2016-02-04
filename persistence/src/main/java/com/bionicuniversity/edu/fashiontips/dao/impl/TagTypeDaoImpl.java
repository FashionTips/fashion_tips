package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.TagType;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class TagTypeDaoImpl extends GenericDaoImpl<TagType, Long> implements TagTypeDao {
    @Override
    public TagType findByType(String type) {
        TypedQuery<TagType> query = em.createQuery("SELECT tagType FROM TagType tagType WHERE tagType.type = :tagType", TagType.class);
        try {
            return query.setParameter("tagType", type).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
