package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.api.util.ImageUtil;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Rest Controller to manage requests, which deal with posts.
 *
 * @author Maksym Dolia
 * @since 26.11.2015.
 */
@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

    @Inject
    private PostService postService;

    @Inject
    private UserService userService;


    /**
     * Returns post from database by given id. Or throws {@code PostNotFoundException} if there is not post
     * with such id.
     *
     * @param id post's id
     * @return post instance
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable long id, Principal principal) {
        User user = principal == null ? null : userService.findOne(principal.getName()).get();
        Post post = postService.get(id, user).orElseThrow(() ->
                new NotFoundException(String.format("The post with id '%d' was not found.", id)));
        ImageUtil.createUrlNameForPost(post);
        if (post.getComments() != null) {
            post.getComments().stream().forEach(comment -> ImageUtil.createUrlNameForUserAvatar(comment.getUser()));
        }
        return post;
    }


    /**
     * Without parameters returned all posts.
     *
     * @param login    optional parameter. If present then returned list user's (login = "login") posts
     * @param hashTag  optional parameter. If present then returned list of posts with this hashtag
     * @param category optional parameter. If present then returned list of posts with preset category
     * @param tag      optional parameter. If present then returned list of posts with this tag
     * @param tagType  optional parameter. If present then returned list of posts with this tagType
     * @param clothes  optional parameter. If present then returned list of posts only with that type of clothes
     * @return list of all posts with such parameters
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Post> findPosts(@RequestParam(value = "author", required = false) String login,
                                @RequestParam(value = "hashtag", required = false) String hashTag,
                                @RequestParam(value = "category", required = false) Post.Category category,
                                @RequestParam(value = "tag", required = false) String tag,
                                @RequestParam(value = "tagType", required = false) String tagType,
                                @RequestParam(value = "clothes", required = false) String clothes,
                                Principal principal) {
        User user = principal == null ? null : userService.findOne(principal.getName()).get();
        List<Post> posts;
        if (login != null) {
            Optional<User> requestUser = userService.findOne(login);
            posts = postService.findAllByUser(requestUser.get(), user);
        } else if (hashTag != null) {
            posts = postService.findAllByHashTag(hashTag, user);
        } else if (category != null) {
            posts = postService.findAllByCategory(category, user);
        } else if (tag != null && tagType != null) {
            posts = postService.findAllByTagAndTagTypeValue(tag, tagType, user);
        } else if (tagType != null) {
            posts = postService.findAllByTagTypeValue(tagType, user);
        } else if (clothes != null) {
            posts = postService.findAllByClothes(clothes, user);
        } else {
            posts = postService.findAll(user);
        }
        return ImageUtil.createUrlNameForPosts(posts);
    }

    /**
     * Adds given post to database.
     *
     * @param post      post to add to database
     * @param principal authenticated user
     * @return response with status 201 (CREATED) and post's data in body
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveNewPost(@Valid @RequestBody Post post, Principal principal) {
        post.setUser(userService.findOne(principal.getName()).get());
        Post savedPost = postService.save(post);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("posts/" + savedPost.getId())
                .build()
                .toUri());
        return new ResponseEntity<>(savedPost, headers, HttpStatus.CREATED);
    }

    /**
     * Deletes post from database with given id.
     *
     * @param id post's id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable long id, Principal principal) {
        User user = userService.findOne(principal.getName())
                .orElseThrow(() -> new RuntimeException("Cannot find the logged in user in db!"));
        postService.delete(id, user);
    }

    /**
     * Updates post with given data.
     *
     * @param id       post's id
     * @param postData new post data
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updatePost(@PathVariable long id, @Valid @RequestBody Post postData) {

        Post post = postService.get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id '%d' was not found.", id)));
        postService.update(postData, post);
    }

    /**
     * Toggles "liked" status for post
     *
     * @param id        post ID
     * @param principal name of logged user
     */
    @RequestMapping(value = "/{id}/liked", method = RequestMethod.POST)
    public void toggleLikedStatus(@PathVariable long id, Principal principal) {
        User user = userService.findOne(principal.getName())
                .orElseThrow(() -> new RuntimeException("Cannot find the logged in user in db!"));
        postService.toggleLikedStatus(id, user);
    }
}
