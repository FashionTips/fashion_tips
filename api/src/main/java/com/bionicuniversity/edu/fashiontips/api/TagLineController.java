package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/posts/tag_lines")
public class TagLineController {

    @Inject
    private TagLineService tagLineService;

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@Valid @RequestBody TagLine tagLine,
                               @RequestParam(value = "image_id", required = true) Long imageId,
                               Principal principal) {
        User user = userService.findOne(principal.getName())
                .orElseThrow(() -> new RuntimeException("Cannot find the logged in user in db!"));
        TagLine savedTagLine = tagLineService.save(tagLine, imageId, user);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/tag_lines/" + savedTagLine.getId())
                .build()
                .toUri())
                .body(savedTagLine);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TagLine getById(@PathVariable long id) {
        return tagLineService.get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tag Line with id '%d' was not found.", id)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable long id, Principal principal) {
        User user = userService.findOne(principal.getName())
                .orElseThrow(() -> new RuntimeException("Cannot find the logged in user in db!"));
        tagLineService.delete(id, user);
    }
}
