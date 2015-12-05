package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.service.PostService;
import com.bionicuniversity.edu.fashiontips.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Rest Controller for tags.
 */
@RestController
@CrossOrigin
@RequestMapping("posts/{id}/tags")
public class TagController {
    @Inject
    private PostService postService;
    @Inject
    private TagService tagService;

    /*Get all tags for POst with ID = {id}*/
    @RequestMapping(method = RequestMethod.GET)
    public Set<Tag> getTags(@PathVariable long id) {
        Post post = postService.get(id);
        return post.getTags();
    }
    /*Save tags for POst with ID = {id} and update post*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveTags(@PathVariable long id, @RequestBody List<String> tagsNames) {
        Set<Tag> tags = tagService.saveTags(tagsNames);
        Post post = postService.get(id);
        post.setTags(tags);
        postService.update(post);
        return new ResponseEntity<>(tags, HttpStatus.CREATED);
    }
}
