package com.bionic.edu.dao;

import com.bionic.edu.entity.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by maxim on 11/6/15.
 */
@Repository
public class PostDaoImpl implements PostDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Post getById(long id) {
        return em.find(Post.class, id);
    }

    @Override
    public List<Post> getAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public void update(Post post) {
        em.merge(post);
    }

    @Override
    public void delete(long id) {
        em.remove(getById(id));
    }

    @Override
    public List<Post> getUserPosts(long userId) {
        return em.createQuery("SELECT p FROM Post p WHERE p.user = :userId", Post.class)
                .setParameter("userId", userId).getResultList();
    }
}
