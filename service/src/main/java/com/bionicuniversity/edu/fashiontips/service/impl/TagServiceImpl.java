package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.service.TagService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tag Service implementation
 */
@Named
public class TagServiceImpl implements TagService {
    @Inject
    private TagDao repository;

    @Override
    public Tag getByName(String tagName) {
        Tag tag = (repository).getByName(tagName);
        if (tag == null) throw new NotFoundException(String.format(
                "User with login %s is not found.", tagName));
        return repository.getByName(tagName);
    }

    /**
     * For a given list of tag names returns a set of Tags.
     * If Tag with required name does not exist then it is first saved.
     *
     * @param tagsNames the List of Tag names
     * @return Set of Tags
     */
    @Override
    public Set<Tag> saveTags(List<String> tagsNames) {
        Set<Tag> tags = new HashSet<>();
        for (String name : tagsNames) {
            Tag tag = repository.getByName(name);
            if (tag == null) {
                tags.add(repository.save(new Tag(name)));
            } else {
                tags.add(tag);
            }
        }
        return tags;
    }
}
