package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.ImageUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

    @Inject
    private ImageUtil imageUtil;

    @Override
    public List<Post> findByUser(User user) {
        List<Post> posts = ((PostDao) repository).findByUser(user);
        posts.forEach(this::addImageUrl);
        return posts;
    }

    @Override
    public List<Post> findByHashTag(String hashTag) {
        List<Post> posts = ((PostDao) repository).findByWord(hashTag).stream().filter(post -> {
            String text = post.getTextMessage();
            return Arrays.asList(text.split("\\s")).stream().anyMatch(s -> s.matches(hashTag + "\\W*"));
        }).collect(Collectors.toList());

        posts.forEach(this::addImageUrl);
        return posts;
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = ((PostDao) repository).findAll();
        posts.forEach(this::addImageUrl);
        return posts;
    }

    @Override
    @Transactional
    public Post get(Long id, User loggedUser) {
        Post post = super.get(id);
        addImageUrl(post);
        post.setLikes((long) post.getLikedByUsers().size());
        if (loggedUser != null)
            if (!post.getUser().equals(loggedUser))
                post.setIsLikedByAuthUser(post.getLikedByUsers().contains(loggedUser) ?
                    Boolean.TRUE : Boolean.FALSE);
        return post;
    }

    @Override
    @Transactional
    public void toggleLike(Long id, User loggedUser) {
        Post post = super.get(id);
        if (post.getUser().equals(loggedUser))
            throw new RuntimeException();
        if (post.getLikedByUsers().contains(loggedUser))
            post.getLikedByUsers().remove(loggedUser);
        else
            post.getLikedByUsers().add(loggedUser);
    }

    private void addImageUrl(Post post) {
        post.getImages().stream().forEach(imageUtil::createUrlName);
    }
}
