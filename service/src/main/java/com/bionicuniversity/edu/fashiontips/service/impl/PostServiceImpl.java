package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.*;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.util.PostUtil;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.*;
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

    @Inject
    private TagDao tagDao;

    @Inject
    private TagTypeDao tagTypeDao;

    @Inject
    private ClothesDao clothesDao;

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllByUser(User user, User loggedUser) {
        List<Post> posts;
        if (!user.equals(loggedUser)) {
            posts = postDao.findByUser(user);
        } else {
            posts = postDao.findForAuthor(user);
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

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAllByTagAndTagTypeValue(String tag, String tagType, User loggedUser) {
        if (!tagDao.existsValue(tag)) return Collections.emptyList();
        TagType existingTagType = tagTypeDao.findByType(tagType);
        if (existingTagType == null) return Collections.emptyList();
        List<Post> posts = postDao.findByTagValueAndTagTypeId(tag, existingTagType.getId());
        PostUtil.normalizeForClient(posts, loggedUser);
        PostUtil.handleDeletedMessages(posts);
        return posts;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAllByTagTypeValue(String tagType, User loggedUser) {
        TagType existingTagType = tagTypeDao.findByType(tagType);
        if (existingTagType == null) return Collections.emptyList();
        List<Post> posts = postDao.findByTagTypeId(existingTagType.getId());
        PostUtil.normalizeForClient(posts, loggedUser);
        PostUtil.handleDeletedMessages(posts);
        return posts;
    }
    @Transactional(readOnly = true)
    @Override
    public List<Post> findAllByClothes(String name, User loggedUser) {
        Clothes clothes = clothesDao.findByName(name);
        if (clothes == null) return Collections.emptyList();
        List<Post> posts = postDao.findByClothesId(clothes.getId());
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
        if(!post.getUser().equals(loggedUser) && post.getStatus() != Post.Status.PUBLISHED){
            throw new NotFoundException(String.format("Post with id '%d' was not found.", id));
        }
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
        boolean notificationEnabled = update.isNotificationEnabled();
        post.setNotificationEnabled(notificationEnabled);
        /* Update logic for status of post */
        Post.Status status = update.getStatus();
        if (status != null) {
            switch (post.getStatus()) {
                case NEW:
                    if (status == Post.Status.PUBLISHED) {
                        post.setStatus(status);
                        post.setPublicationTime(LocalDateTime.now());
                    } else if (status == Post.Status.SCHEDULED) {
                        if (update.getPublicationTime() != null && update.getPublicationTime().isAfter(LocalDateTime.now())) {
                            post.setStatus(status);
                            post.setPublicationTime(update.getPublicationTime());
                        } else throw new IllegalArgumentException("Published time is not valid.");
                    } else if (status != Post.Status.NEW) {
                        throw new IllegalArgumentException("Status have invalid value.");
                    }
                    break;
                case SCHEDULED:
                    if (status == Post.Status.SCHEDULED) {
                        if (update.getPublicationTime() != null && update.getPublicationTime().isBefore(LocalDateTime.now())) {
                            post.setPublicationTime(update.getPublicationTime());
                        } else throw new IllegalArgumentException("Published time is not valid.");
                    } else if (status == Post.Status.PUBLISHED) {
                        post.setStatus(status);
                        post.setPublicationTime(LocalDateTime.now());
                    } else if (status == Post.Status.NEW) {
                        post.setStatus(status);
                        post.setPublicationTime(null);
                    } else throw new IllegalArgumentException("Status have invalid value.");
                    break;
                case PUBLISHED:
                    if (status == Post.Status.HIDDEN) {
                        post.setStatus(status);
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

        /* Status logic, when post is creating */
        if (post.getStatus() == null) {
            post.setStatus(Post.Status.PUBLISHED);
            post.setPublicationTime(LocalDateTime.now());
        } else {
            switch (post.getStatus()) {
                case NEW:
                    post.setPublicationTime(null);
                    break;
                case PUBLISHED:
                    post.setPublicationTime(LocalDateTime.now());
                    break;
                case SCHEDULED:
                    if (post.getPublicationTime() == null || post.getPublicationTime().isBefore(LocalDateTime.now())) {
                        throw new IllegalArgumentException("Published time is not valid.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Status have invalid value.");
            }
        }
        return postDao.save(post);
    }

    @Override
    public List<User> getLikedUsers(long id) {
        return postDao.getLikedUsers(id);
    }
}
