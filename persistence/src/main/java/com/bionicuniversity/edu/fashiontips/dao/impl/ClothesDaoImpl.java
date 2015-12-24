package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ClothesDaoImpl extends GenericDaoImpl<Clothes, Long> implements ClothesDao {

    @Override
    public Clothes findClothesByName(String clothesName) {
        TypedQuery<Clothes> query =
                em.createQuery("SELECT DISTINCT c FROM Clothes c WHERE c.name = :clothesName", Clothes.class);
        query.setParameter("clothesName", clothesName);
        return query.getSingleResult();
    }
}
