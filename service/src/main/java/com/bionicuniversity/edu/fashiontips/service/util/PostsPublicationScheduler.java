package com.bionicuniversity.edu.fashiontips.service.util;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Utility class for execute some action on schedule
 * Created by Sergiy on 1/20/2016.
 */
@Component
public class PostsPublicationScheduler {
    @Inject
    PostDao postDao;

    public void publication() {
        List<Post> posts = postDao.findUnpublished();
        posts.stream()
                .peek(post -> post.setCommentsAllowed(true))
                .peek(post -> post.setStatus(Post.Status.PUBLISHED))
                .forEach(post -> postDao.save(post));
    }
}