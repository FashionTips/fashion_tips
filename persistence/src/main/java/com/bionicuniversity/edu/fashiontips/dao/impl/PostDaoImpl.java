package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.entity.Post.Category;

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
        TypedQuery<Post> query = em.createNamedQuery("Post.findByUser", Post.class);
        return query.setParameter("user", user).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findForAuthor(User author) {
        TypedQuery<Post> query = em.createNamedQuery("Post.findForAuthor", Post.class);
        return query.setParameter("author", author).getResultList();
    }

    @Override
    public List<Post> findByWord(String word) {
        TypedQuery<Post> query = em.createNamedQuery("Post.findByWord", Post.class);
        return query.setParameter("pattern", "%" + word + "%").setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findByCategory(Category category) {
        TypedQuery<Post> query = em.createNamedQuery("Post.findByCategory", Post.class);
        return query.setParameter("category", category).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> query = em.createNamedQuery("Post.findAll", Post.class);
        return query.setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findUnpublished() {
        TypedQuery<Post> query = em.createNamedQuery("Post.findUnpublished", Post.class);
        return query.setParameter("wait", Post.Status.SCHEDULED).setParameter("now", LocalDateTime.now()).getResultList();
    }

    @Override
    public List<Post> findByTagValueAndTagTypeId(String value, Long tagTypeId) {
        TypedQuery<Post> query = em.createNamedQuery("Post.findByTagValueAndTagTypeId", Post.class);
        return query.setParameter("tagValue", value).setParameter("tagTypeId", tagTypeId).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findByTagTypeId(Long tagTypeId) {
        TypedQuery<Post> query = em.createNamedQuery("Post.findByTagTypeId", Post.class);
        return query.setParameter("tagType_id", tagTypeId).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<Post> findByClothesId(Long clothesId) {
        TypedQuery<Post> query = em.createNamedQuery("Post.findByClothesId", Post.class);
        return  query.setParameter("clothesId", clothesId).setParameter("published", Post.Status.PUBLISHED).getResultList();
    }

    @Override
    public List<User> getLikedUsers(Long id) {
        Query query =
                em.createNamedQuery("Post.getLikedUsers").setParameter("id", id);
        List<User> users = query.getResultList();
        return users;
    }
}