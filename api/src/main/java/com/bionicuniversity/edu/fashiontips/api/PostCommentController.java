package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Collection;

/**
 * Controller to deal with request concerning post comments.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 13.12.2015
 */

@RestController
@CrossOrigin
@RequestMapping("posts/{postId}/comments")
public class PostCommentController {

    @Inject
    private PostService postService;

    @Inject
    private UserService userService;

    @Inject
    private CommentService commentService;

    /**
     * Saves post.
     *
     * @param comment   comment to save
     * @param postId    post's id
     * @param principal authenticated user
     * @return saved comment
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Comment comment, @PathVariable long postId, Principal principal) {
        comment.setUser(userService.getByLogin(principal.getName()));
        Comment savedComment = commentService.save(comment, postId);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("posts/" + postId + "/comments/" + savedComment.getId())
                        .build()
                        .toUri())
                .body(savedComment);
    }

    /**
     * Retrieves and returns post's comments.
     *
     * @param postId post's id
     * @return collection of comments, which are belongs to given post
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Comment> getAll(@PathVariable long postId) {
        return commentService.findAllByPostId(postId);
    }
}
