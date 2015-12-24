package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA implementation of {@code TagLineDao} interface.
 *
 * @author Vadym Golub
 * @author Alexandr Laktionov
 */
@Repository
public class TagLineDaoImpl extends GenericDaoImpl<TagLine, Long> implements TagLineDao {

    @Override
    public List<TagLine> getAllByClothes(Clothes clothesTag) {
        TypedQuery<TagLine> query =
                em.createQuery("SELECT DISTINCT tg FROM TagLine tg WHERE tg.clothes.id=:clothesTagId", TagLine.class);
        query.setParameter("clothesTagId", clothesTag.getId());
        return query.getResultList();
    }
}
