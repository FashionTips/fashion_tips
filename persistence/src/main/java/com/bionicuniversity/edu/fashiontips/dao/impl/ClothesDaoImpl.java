package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class ClothesDaoImpl extends GenericDaoImpl<Clothes, Long> implements ClothesDao {
    @Override
    public Clothes findByName(String name) {
        TypedQuery<Clothes> query = em.createNamedQuery("Clothes.findByName", Clothes.class);
        try {
            return query.setParameter("name", name).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
