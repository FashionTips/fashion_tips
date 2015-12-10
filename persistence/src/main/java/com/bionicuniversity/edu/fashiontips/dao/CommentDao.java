package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.Comment;

import java.util.List;

/**
 * Created by slav9nin on 08.12.2015.
 */
public interface CommentDao extends GenericDao<Comment, Long> {

    List<Comment> getCommentsByPost(Post post);
}
