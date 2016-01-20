package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.api.util.ImageUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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
        User user = principal == null ? null : userService.findOne(principal.getName());
        Post post = postService.get(id, user);
        ImageUtil.createUrlNameForPost(post);
        if (post.getComments() != null) {
            post.getComments().stream().forEach(comment -> ImageUtil.createUrlNameForUserAvatar(comment.getUser()));
        }
        return post;
    }


    /**
     * Without parameters returned all posts.
     *
     * @param login        optional parameter. If present then returned list user's (login = "login") posts
     * @param hashTag      optional parameter. If present then returned list of posts with this hashtag
     * @param categoryName optional parameter. If present then returned list of posts with preset category
     * @return list of all posts with such parameters
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Post> findPosts(@RequestParam(value = "author", required = false) String login,
                                @RequestParam(value = "hashtag", required = false) String hashTag,
                                @RequestParam(value = "category", required = false) String categoryName,
                                Principal principal) {
        User user = principal == null ? null : userService.findOne(principal.getName());
        List<Post> posts;
        if (login != null) {
            posts = postService.findAllByUser(userService.findOne(login), user);
        } else if (hashTag != null) {
            posts = postService.findAllByHashTag(hashTag, user);
        } else if (categoryName != null) {
            posts = postService.findAllByCategory(categoryName, user);
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
    public ResponseEntity<?> saveNewPost(@Valid @RequestBody Post post,
                                         @RequestParam(value = "hide", required = false) String hide,
                                         @RequestParam(value = "time", required = false) String time,
                                         Principal principal) {
        post.setUser(userService.findOne(principal.getName()));

        if (!"true".equals(hide) && time == null) {
            post.setStatusByName("PUBLISHED");
        }else if("true".equals(hide)){
            post.setStatusByName("NEW");
        } else if (time != null) {
            post.setStatusByName("PUBLICATION_PENDING");
        }
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
    public void deletePost(@PathVariable long id) {
        Post post = postService.get(id);
        postService.delete(post);
    }

    /**
     * Updates post with given data.
     *
     * @param id       post's id
     * @param postData new post data
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updatePost(@PathVariable long id, @Valid @RequestBody Post postData) {

        Post post = postService.get(id);
        String title = postData.getTitle();
        if (title != null) post.setTitle(title);
        String message = postData.getTextMessage();
        if (message != null) post.setTextMessage(message);
        Post.Category category = postData.getCategory();
        if (category != null) post.setCategory(category);
        List<Image> images = postData.getImages();
        if (images != null) post.setImages(images);
        postService.update(post);
    }

    /**
     * Toggles "liked" status for post
     *
     * @param id        post ID
     * @param principal name of logged user
     */
    @RequestMapping(value = "/{id}/liked", method = RequestMethod.POST)
    public void toggleLikedStatus(@PathVariable long id, Principal principal) {
        User user = userService.findOne(principal.getName());
        postService.toggleLikedStatus(id, user);
    }
}
