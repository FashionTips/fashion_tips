package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.entity.TagType;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class TagLineServiceImpl implements TagLineService {

    @Inject
    private TagLineDao tagLineDao;

    @Inject
    private ClothesDao clothesDao;

    @Inject
    private TagTypeDao tagTypeDao;

    @Inject
    private PostDao postDao;

    @Override
    public List<Clothes> getAvailableTypeOfClothes() {
        return clothesDao.getAll();
    }

    @Override
    public List<TagType> getExistingTagTypes() {
        return tagTypeDao.getAll();
    }

    @Transactional
    @Override
    public TagLine save(TagLine tagLine, long postId) {
        if(postDao.exists(postId)){
            Post postRelatedToTagLine = postDao.getReference(postId);
            tagLine.setPost(postRelatedToTagLine);
            return tagLineDao.save(tagLine);
        } else {
            throw new NotFoundException(String.format("Could not found post with id = %d", postId));
        }
    }

    @Override
    public TagLine get(long id) {
        TagLine tagLine = tagLineDao.getById(id);
        if (tagLine != null) {
            return tagLine;
        } else {
            throw new NotFoundException(String.format("TagLine not found by id=%d", id));
        }
    }
}
