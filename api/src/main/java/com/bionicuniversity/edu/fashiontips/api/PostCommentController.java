package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.api.util.ImageUtil;
import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.CommentService;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

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
    public ResponseEntity save(@RequestBody @Valid Comment comment, @PathVariable long postId, Principal principal) {

        String login = principal.getName();
        User user = userService.findOne(login).orElseThrow(() ->
                new NotFoundException(String.format("User with name '%s' was not found.", login)));
        comment.setUser(user);

        Comment savedComment = commentService.save(comment, postId);
        ImageUtil.createUrlNameForUserAvatar(savedComment.getUser());

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
        List<Comment> comments = commentService.findAllByPostId(postId);
        comments.stream().forEach(comment -> ImageUtil.createUrlNameForUserAvatar(comment.getUser()));
        return comments;
    }

    /**
     * Allows or prohibits posts to be commented.
     *
     * @param postId post's id
     * @return actual status of ability to comment post after all done manipulations
     */
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public boolean block(@PathVariable long postId) {

        Post post = postService.get(postId)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id '%d' was not found.", postId)));

        return commentService.block(post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity hide(@PathVariable long id, Principal principal){
        commentService.hideById(id, principal.getName());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid Comment comment, Principal principal) {
        comment.setId(id);
        Comment updatedComment = commentService.update(comment, principal.getName());
        ImageUtil.createUrlNameForUserAvatar(updatedComment.getUser());
        return ResponseEntity.ok(updatedComment);
    }
}
