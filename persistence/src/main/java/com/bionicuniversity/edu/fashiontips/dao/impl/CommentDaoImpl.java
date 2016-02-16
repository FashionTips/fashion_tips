package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * CommentDao class to do CRUD ops
 *
 * @author Alexander Laktionov
 * @since 8/12/2015
 */

@Repository
public class CommentDaoImpl extends GenericDaoImpl<Comment, Long> implements CommentDao {

    @Override
    public List<Comment> findAllByPost(Post post) {
        TypedQuery<Comment> query = em.createNamedQuery("Comment.findAllByPost", Comment.class);
        query.setParameter("id", post.getId());
        return query.getResultList();
    }
}
