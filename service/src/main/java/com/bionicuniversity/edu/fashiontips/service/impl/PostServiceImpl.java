package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.PostUtil;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
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

    @Inject
    private PostDao postDao;

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllByUser(User user, User loggedUser) {
        List<Post> posts;
        if (!user.equals(loggedUser)) {
            posts = postDao.findByUser(user);
        } else {
            posts = postDao.findMine(user);
        }
        PostUtil.normalizeForClient(posts, loggedUser);
        PostUtil.handleDeletedMessages(posts);
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllByHashTag(String hashTag, User loggedUser) {
        List<Post> posts = postDao.findByWord(hashTag).stream().filter(post -> {
            String text = post.getTextMessage();
            return Arrays.asList(text.split("\\s")).stream().anyMatch(s -> s.matches(hashTag + "\\W*"));
        }).collect(Collectors.toList());
        PostUtil.normalizeForClient(posts, loggedUser);
        PostUtil.handleDeletedMessages(posts);
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllByCategory(Post.Category category, User loggedUser) {
        List<Post> posts = postDao.findByCategory(category);
        PostUtil.normalizeForClient(posts, loggedUser);
        PostUtil.handleDeletedMessages(posts);
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll(User loggedUser) {
        List<Post> posts = postDao.findAll();
        PostUtil.normalizeForClient(posts, loggedUser);
        PostUtil.handleDeletedMessages(posts);
        return posts;
    }

    @Override
    @Transactional
    // don't have message handle
    public Optional<Post> get(long id) {
        return Optional.ofNullable(postDao.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> get(long id, User loggedUser) {
        Post post = postDao.getById(id);
        PostUtil.normalizeForClient(post, loggedUser);
        PostUtil.handleDeletedMessages(post);
        return Optional.ofNullable(post);
    }

    @Override
    @PreAuthorize("#post.user.login == authentication.name")
    public void update(Post update, Post post) {

        String title = update.getTitle();
        if (title != null) post.setTitle(title);
        String message = update.getTextMessage();
        if (message != null) post.setTextMessage(message);
        Post.Category category = update.getCategory();
        if (category != null) post.setCategory(category);
        List<Image> images = update.getImages();
        if (images != null) post.setImages(images);
        boolean commentsAllowed = update.isCommentsAllowed();
        post.setCommentsAllowed(commentsAllowed);

        //Set status and publication time
        Post.Status status = update.getStatus();
        LocalDateTime updatePublicationTime = update.getPublicationTime();

        if (updatePublicationTime != null && !updatePublicationTime.equals(post.getPublicationTime())) {
            if (updatePublicationTime.isAfter(LocalDateTime.now())) {
                if (post.getStatus() == Post.Status.NEW || post.getStatus() == Post.Status.WAIT) {
                    post.setStatus(Post.Status.WAIT);
                    post.setPublicationTime(updatePublicationTime);
                    post.setCommentsAllowed(false);
                } else {
                    throw new IllegalArgumentException("Status or publication time have invalid value.");
                }
            } else {
                if (post.getStatus() == Post.Status.NEW || post.getStatus() == Post.Status.WAIT) {
                    post.setStatus(Post.Status.PUBLISHED);
                    post.setPublicationTime(LocalDateTime.now());
                } else {
                    throw new IllegalArgumentException("Status or publication time have invalid value.");
                }
            }
        } else if (status != null) {
            switch (post.getStatus()) {
                case NEW:
                case WAIT:
                    if (status == Post.Status.PUBLISHED) {
                        post.setPublicationTime(LocalDateTime.now());
                        post.setStatus(status);
                    } else if (status == Post.Status.NEW || status == Post.Status.WAIT) {
                        post.setCommentsAllowed(false);
                    } else {
                        throw new IllegalArgumentException("Status have invalid value.");
                    }
                    break;
                case PUBLISHED:
                    if (status == Post.Status.HIDDEN) {
                        post.setStatus(status);
                        post.setCommentsAllowed(false);
                    } else if (status != Post.Status.PUBLISHED) {
                        throw new IllegalArgumentException("Status have invalid value.");
                    }
                    break;
                case HIDDEN:
                    if (status == Post.Status.PUBLISHED) {
                        post.setStatus(status);
                    } else if (status != Post.Status.HIDDEN) {
                        throw new IllegalArgumentException("Status have invalid value.");
                    }
                    break;
            }
        }
        postDao.save(post);
    }

    @Override
    @Transactional
    public void delete(long id, User loggedUser) {
        Post post = postDao.getById(id);
        if (post == null) throw new NotFoundException(String.format("Post with id '%d' was not found.", id));
        if (!(post.getUser().equals(loggedUser))) throw new AccessDeniedException("Post belongs to different user.");
        postDao.delete(id);
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

        //Set status and publication time
        if(post.getPublicationTime() != null && post.getPublicationTime().isAfter(LocalDateTime.now())){
            post.setStatus(Post.Status.WAIT);
            post.setCommentsAllowed(false);
        } else{
            if(post.getStatus() != null){
                post.setPublicationTime(LocalDateTime.now());
            } else{
                post.setStatus(Post.Status.PUBLISHED);
                post.setPublicationTime(LocalDateTime.now());
            }
        }
        return postDao.save(post);
    }
}
