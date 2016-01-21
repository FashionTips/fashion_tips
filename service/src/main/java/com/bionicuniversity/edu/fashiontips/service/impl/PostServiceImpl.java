package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.PostUtil;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Post Service Implementation.
 *
 * @author Sergiy
 * @author Volodymyr Portianko
 * @author Maksym Dolia
 * @since 25/11/2015
 */
@Named
public class PostServiceImpl implements PostService {

    @Inject private PostDao postDao;

    @Override
    @Transactional
    public List<Post> findAllByUser(User user, User loggedUser) {
        List<Post> posts = postDao.findByUser(user);
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findAllByHashTag(String hashTag, User loggedUser) {
        List<Post> posts = postDao.findByWord(hashTag).stream().filter(post -> {
            String text = post.getTextMessage();
            return Arrays.asList(text.split("\\s")).stream().anyMatch(s -> s.matches(hashTag + "\\W*"));
        }).collect(Collectors.toList());
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findAllByCategory(Post.Category category, User loggedUser) {
        List<Post> posts = postDao.findByCategory(category);
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findAll(User loggedUser) {
        List<Post> posts = postDao.findAll();
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }

    @Override
    @Transactional
    public Optional<Post> get(long id) {
        return Optional.ofNullable(postDao.getById(id));
    }

    @Override
    @Transactional
    public Optional<Post> get(long id, User loggedUser) {
        Post post = postDao.getById(id);
        PostUtil.normalizeForClient(post, loggedUser);
        return Optional.ofNullable(post);
    }

    @Override
    @PreAuthorize("#post.user.login == authentication.name")
    public void update(Post post) {
        postDao.save(post);
    }

    @Override
    @PreAuthorize("#post.user.login == authentication.name")
    public void delete(@P("post") Post post) {
        Objects.requireNonNull(post, "Post cannot be null.");
        postDao.delete(post);
    }

    @Override
    @Transactional
    public void toggleLikedStatus(long id, User loggedUser) {
        Post post = postDao.getById(id);
        if (!post.getUser().equals(loggedUser)) {
            if (post.getLikedByUsers().contains(loggedUser))
                post.getLikedByUsers().remove(loggedUser);
            else
                post.getLikedByUsers().add(loggedUser);
        } else {
            throw new IllegalArgumentException("Users can not 'liked' their own posts");
        }
    }

    @Override
    @Transactional
    public Post save(Post post) {
        Objects.requireNonNull(post);
        if (post.getTagLines() != null && post.getTagLines().size() > 0) {
            post.getTagLines().stream().forEach(tagLine -> tagLine.setPost(post));
        }
        return postDao.save(post);
    }
}
