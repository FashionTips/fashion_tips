package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.HashTagDao;
import com.bionicuniversity.edu.fashiontips.entity.HashTag;
import com.bionicuniversity.edu.fashiontips.service.HashTagService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bionicuniversity.edu.fashiontips.service.util.Regex.parseHashTags;

/**
 * HashTag Service implementation
 */
@Named
public class HashTagServiceImpl implements HashTagService {
    @Inject
    private HashTagDao repository;

    @Override
    public HashTag getByName(String hashTagName) {
        return repository.getByName(hashTagName);
    }

    @Override
    public Set<HashTag> saveHashTags(String text) {
        Set<HashTag> tags = new HashSet<>();
        List<String> hashTagsNames = parseHashTags(text);
        for (String name : hashTagsNames) {
            HashTag hashTag = repository.getByName(name);
            if (hashTag == null) {
                tags.add(repository.save(new HashTag(name)));
            } else {
                tags.add(hashTag);
            }
        }
        return tags;
    }
}