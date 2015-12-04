package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Category;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
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
    public Post getPost(@PathVariable long id) {
        return postService.get(id);
    }

    /**
     * Returns all user's posts.
     *
     * @param principal authenticated user
     * @return list of posts
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Post> getPosts(Principal principal) {
        User user = userService.getByLogin(principal.getName());
        return postService.getAllByUser(user);
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
        post.setCategory(Category.POST);    // temporary workaround
        post.setUser(userService.getByLogin(principal.getName()));
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
        postService.delete(id);
    }

    /**
     * Updates post with given data.
     *
     * @param id   post's id
     * @param post new post data
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updatePost(@PathVariable long id, @Valid @RequestBody Post post) {
        post.setId(id);
        postService.save(post);
    }
}
