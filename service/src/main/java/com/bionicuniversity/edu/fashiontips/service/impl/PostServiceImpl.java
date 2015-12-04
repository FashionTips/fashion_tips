package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;

import javax.inject.Named;
import java.util.List;

/**
 * Created by Sergiy on 25.11.2015
 * Project: fashion-tips
 * Post Service Implementation.
 */
@Named
public class PostServiceImpl extends GenericServiceImpl<Post, Long> implements PostService {

    @Override
    public List<Post> getAllByUser(User user) {
        return ((PostDao) repository).getAllByUser(user);
    }
}
