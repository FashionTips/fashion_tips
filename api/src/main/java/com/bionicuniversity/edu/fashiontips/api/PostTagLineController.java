package com.bionicuniversity.edu.fashiontips.api;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.entity.TagType;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostTagLineController {

    @Inject
    private TagLineService tagLineService;

    @RequestMapping(value = "/tag_lines/clothes", method = RequestMethod.GET)
    public List<Clothes> getClothes() {
        return tagLineService.getAvailableTypeOfClothes();
    }

    @RequestMapping(value = "/tag_lines/tags/types", method = RequestMethod.GET)
    public List<TagType> getTagTypes() {
        return tagLineService.getExistingTagTypes();
    }

    @RequestMapping(value = "{postId}/tag_lines", method = RequestMethod.POST)
    public ResponseEntity save(@Valid @RequestBody TagLine tagLine, @PathVariable long postId) {
        TagLine savedTagLine = tagLineService.save(tagLine, postId);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("posts/" + postId + "/tag_lines/" + savedTagLine.getId())
                .build()
                .toUri())
                .body(savedTagLine);
    }

    @RequestMapping(value = "/tag_lines/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable long id){
        return ResponseEntity.ok(tagLineService.get(id));
    }
}
