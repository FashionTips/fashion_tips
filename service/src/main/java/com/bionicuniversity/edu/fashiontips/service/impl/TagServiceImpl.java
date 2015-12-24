package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.service.TagService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Alexandr Laktionov
 */
@Named
public class TagServiceImpl implements TagService {

    @Inject
    private TagDao tagDao;

    @Override
    public Tag findTag(String tagName) {
        Tag tag = null;
        try {
            tag = tagDao.findTag(tagName);
        } catch (RuntimeException rte) {
            throw new NotFoundException(String.format("Tag with tag's name %s not found", tagName));
        }
        return tag;
    }

    @Override
    public List<TagLine> findTagLinesByTag(Tag tag) {
        return tagDao.findTagLinesByTag(tag);
    }
}
