package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.ImageUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Post Service Implementation.
 *
 * @author Sergiy
 * @author Volodymyr Portianko
 */
@Named
public class PostServiceImpl extends GenericServiceImpl<Post, Long> implements PostService {

    @Inject
    private ImageUtil imageUtil;

    @Override
    public List<Post> getAllByUser(User user) {
        List<Post> posts = ((PostDao) repository).getAllByUser(user);
        posts.forEach(this::addImageUrl);
        return posts;
    }

    @Override
    public Post get(Long id) {
        Post post = super.get(id);
        addImageUrl(post);
        return post;
    }

    private void addImageUrl(Post post) {
        post.getImages().stream().forEach(imageUtil::createUrlName);
    }
}
