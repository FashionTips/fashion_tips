package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import static com.bionicuniversity.edu.fashiontips.entity.Post.*;
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
    public List<Post> findByCategory(String categoryName) {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.category = :category ORDER BY p.created DESC", Post.class);
        return query.setParameter("category", Category.valueOf(categoryName)).getResultList();
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p ORDER BY p.created DESC", Post.class);
        return query.getResultList();
    }
}
