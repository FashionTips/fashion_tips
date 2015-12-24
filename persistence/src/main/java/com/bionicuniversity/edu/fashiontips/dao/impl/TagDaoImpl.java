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
