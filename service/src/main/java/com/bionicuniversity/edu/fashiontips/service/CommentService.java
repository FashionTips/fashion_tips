package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;

import java.util.List;

/**
 * @author alaktionov aka slav9nin
 */
public interface CommentService extends GenericService<Comment,Long> {
    List<Comment> getCommentsByPost(Post post);
}
