package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.ImageUtil;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<Post> getAllByUser(User user) {
        List<Post> posts = ((PostDao) repository).getAllByUser(user);
        posts.forEach(this::addImageUrl);
        posts.forEach(post -> setLikeParameters(post, user));
        return posts;
    }

    @Override
    @Transactional
    public Post get(Long id, User loggedUser) {
        Post post = super.get(id);
        addImageUrl(post);
        setLikeParameters(post, loggedUser);
        return post;
    }

    @Override
    @Transactional
    public void toggleLikedStatus(Long id, User loggedUser) {
        Post post = super.get(id);
        if (!post.getUser().equals(loggedUser)) {
            if (post.getLikedByUsers().contains(loggedUser))
                post.getLikedByUsers().remove(loggedUser);
            else
                post.getLikedByUsers().add(loggedUser);
        } else {
            throw new IllegalArgumentException("Users can not 'liked' their own posts");
        }
    }

    private void addImageUrl(Post post) {
        post.getImages().stream().forEach(imageUtil::createUrlName);
    }

    /**
     * Method for setting necessary for clients parameters, related to "likes"
     *
     * @param post post entity
     * @param loggedUser authenticated user entity
     */
    private void setLikeParameters(Post post, User loggedUser) {
        post.setLikes((long) post.getLikedByUsers().size());
        if (loggedUser != null)
            if (!post.getUser().equals(loggedUser))
                post.setIsLikedByAuthUser(post.getLikedByUsers().contains(loggedUser) ?
                        Boolean.TRUE : Boolean.FALSE);
    }
}
