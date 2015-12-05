package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * Tag Service interface
 */
public interface TagService {
    /*Takes list of tags names, returned set of Tags*/
    Set<Tag> saveTags(List<String> tagsNames);
}
