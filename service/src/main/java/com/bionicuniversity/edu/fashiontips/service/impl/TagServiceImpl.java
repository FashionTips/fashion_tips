package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.service.TagService;

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
    public Set<Tag> saveTags(List<String> tagsNames) {
        Set<Tag> tagsSet = new HashSet<>();
        for (String name : tagsNames) {
            Tag tag = repository.getByName(name);
            if (tag == null) {
                tagsSet.add(repository.save(new Tag(name)));
            } else {
                tagsSet.add(tag);
            }
        }
        return tagsSet;
    }
}
