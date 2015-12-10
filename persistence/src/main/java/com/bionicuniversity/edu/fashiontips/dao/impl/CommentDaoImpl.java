package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * CommentDao class to do CRUD ops
 * @author alaktionov
 */

@Repository
public class CommentDaoImpl extends GenericDaoImpl<Comment,Long> implements CommentDao {

    /**
     *
     * @param post {@link Post} which represent user's post
     * @return {@link List} of comments by user's post
     */
    @Override
    public List<Comment> getCommentsByPost(Post post) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :id", Comment.class);
        query.setParameter("id", post.getId());
        return query.getResultList();
    }
}
