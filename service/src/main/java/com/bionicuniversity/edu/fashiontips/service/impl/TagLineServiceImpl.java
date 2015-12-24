package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.*;
import com.bionicuniversity.edu.fashiontips.entity.*;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import com.bionicuniversity.edu.fashiontips.service.util.PostUtil;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class TagLineServiceImpl implements TagLineService {

    @Inject
    private TagLineDao tagLineDao;

    @Inject
    private ClothesDao clothesDao;

    @Inject
    private TagTypeDao tagTypeDao;

    @Inject
    private TagDao tagDao;

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

    @Override
    @Transactional
    public List<Post> findAllByTag(Tag tag, User loggedUser) {
        List<Post> posts = tagDao.findTagLinesByTag(tag).parallelStream().flatMap( tagTag ->
                Arrays.asList(tagTag.getPost()).stream()).distinct().collect(Collectors.toList());
        PostUtil.normalizeForClient(posts, loggedUser);
        return posts;
    }
}
