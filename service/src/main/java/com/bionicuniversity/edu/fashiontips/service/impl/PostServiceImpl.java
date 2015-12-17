package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.PostUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Post Service Implementation.
 *
 * @author Sergiy
 * @author Volodymyr Portianko
 */
@Named
public class PostServiceImpl extends GenericServiceImpl<Post, Long> implements PostService {

    @Override
    @Transactional
    public List<Post> findAllByUser(User user, User loggedUser) {
        List<Post> posts = ((PostDao) repository).findByUser(user);
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findAllByHashTag(String hashTag, User loggedUser) {
        List<Post> posts = ((PostDao) repository).findByWord(hashTag).stream().filter(post -> {
            String text = post.getTextMessage();
            return Arrays.asList(text.split("\\s")).stream().anyMatch(s -> s.matches(hashTag + "\\W*"));
        }).collect(Collectors.toList());
        PostUtil.normalizeForClient(posts,loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findAll(User loggedUser) {
        List<Post> posts = ((PostDao) repository).findAll();
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public Post get(Long id, User loggedUser) {
        Post post = super.get(id);
        PostUtil.normalizeForClient(post, loggedUser);
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
}
