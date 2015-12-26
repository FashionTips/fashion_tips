package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@code PostDao} interface for relational database.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 25/11/2015
 */
@Repository
public class PostDaoImpl extends GenericDaoImpl<Post, Long> implements PostDao {

    @Override
    public List<Post> findByUser(User user) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.user = :user ORDER BY p.created DESC", Post.class);
        return query.setParameter("user", user).getResultList();
    }

    @Override
    public List<Post> findByWord(String word) {
        TypedQuery<Post> query =
                em.createQuery("SELECT p FROM Post p WHERE p.textMessage LIKE :pattern ORDER BY p.created DESC", Post.class);

        return query.setParameter("pattern", "%" + word + "%").getResultList();
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p ORDER BY p.created DESC", Post.class);
        return query.getResultList();
    }

    @Override
    public List<Post> getAllByTag(Tag tag) {
        Query query = em.createNativeQuery("SELECT DISTINCT post.* FROM POSTS post " +
                "INNER JOIN TAG_LINES tglines ON post.ID = tglines.POST_ID " +
                "INNER JOIN TAGS_TAG_LINES tag_taglines ON tag_taglines.TAG_LINE_ID = tglines.ID " +
                "INNER JOIN TAGS tags ON tag_taglines.TAG_ID = tags.ID " +
                "WHERE tags.ID = :tagId", Post.class);
        query.setParameter("tagId", tag.getId());
        return query.getResultList();
    }

    @Override
    public List<Post> getAllByClothes(Clothes clothes) {
        Query query = em.createNativeQuery("SELECT DISTINCT post.* FROM POSTS post " +
                "INNER JOIN TAG_LINES tglines ON post.ID = tglines.POST_ID " +
                "INNER JOIN CLOTHES clothes ON clothes.ID = tglines.CLOTHES_ID " +
                "WHERE clothes.ID = :clothesId", Post.class);
        query.setParameter("clothesId", clothes.getId());
        return query.getResultList();
    }
}
