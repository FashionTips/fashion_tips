package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;

import java.util.List;

/**
 * @author Alexandr Laktionov
 */
public interface TagService {

    Tag findTag(String tagName);

    List<TagLine> findTagLinesByTag(Tag tag);

}
