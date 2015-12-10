package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.CommentDao;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.service.CommentService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author alaktionov aka slav9nin
 */

@Named
public class CommentServiceImpl extends GenericServiceImpl<Comment,Long> implements CommentService {

    @Inject
    private CommentDao commentDao;

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return commentDao.getCommentsByPost(post);
    }
}
