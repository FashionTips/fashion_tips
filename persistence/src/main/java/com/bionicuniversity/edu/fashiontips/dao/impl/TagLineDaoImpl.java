package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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

    @Override
    public List<TagLine> findTagLinesByTag(Tag tag) {
        Query query = em.createNativeQuery("SELECT DISTINCT tgline.* FROM TAG_LINES tgline " +
                "INNER JOIN TAGS_TAG_LINES tag_taglines ON tag_taglines.TAG_LINE_ID = tgline.ID " +
                "INNER JOIN TAGS tags ON tag_taglines.TAG_ID = tags.ID " +
                "WHERE tags.ID = :tagId", TagLine.class);
        query.setParameter("tagId", tag.getId());
        return query.getResultList();
    }
}
