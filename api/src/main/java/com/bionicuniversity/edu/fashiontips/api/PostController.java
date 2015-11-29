package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Category;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
     * @return  post instance
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable long id) {
        validatePost(id);
        return postService.get(id);
    }

    /**
     * Adds given post to database.
     *
     * @param post post to add to database
     * @return response with status 201 (CREATED) and post's data in body
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveNewPost(@RequestBody Post post, Principal principal) {
        post.setCategory(Category.POST);
        post.setUser(userService.getByLogin(principal.getName()));
        Post savedPost = postService.save(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    /**
     * Deletes post from database with given id.
     *
     * @param id post's id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable long id) {
        validatePost(id);
        postService.delete(id);
    }

    /* Check is there post in db with given id */
    private void validatePost(long id) {
        if(postService.get(id) == null) {
            throw new PostNotFoundException(id);
        }
    }
}


/**
 *  Exception, which determines that post is not present in database. Sets the HttpStatus to 404 (Not Found)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("Post doesn’t exist");
    }

    public PostNotFoundException(long id) {
        super("Could not find post with id " + id + ".");
    }
}
