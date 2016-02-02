package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.ClothesDao;
import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.dao.TagTypeDao;
import com.bionicuniversity.edu.fashiontips.entity.*;
import com.bionicuniversity.edu.fashiontips.service.ImageService;
import com.bionicuniversity.edu.fashiontips.service.TagLineService;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotAllowedActionException;
import com.bionicuniversity.edu.fashiontips.service.util.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Named
public class TagLineServiceImpl implements TagLineService {

    @Inject
    private TagLineDao tagLineDao;

    @Inject
    private ClothesDao clothesDao;

    @Inject
    private TagTypeDao tagTypeDao;

    @Inject
    private ImageService imageService;

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
    public TagLine save(TagLine tagLine, Long imageId, User loggedUser) {
        Objects.requireNonNull(tagLine, "Tag Line should not be null.");
        Image image = imageService.findOne(imageId)
                .orElseThrow(() -> new NotFoundException(String.format("Image with id=%s is not found.",imageId)));
        tagLine.setImage(image);
        if (image.getOwner().equals(loggedUser)) {
            return tagLineDao.save(tagLine);
        } else {
            throw new NotAllowedActionException("Users can not attach tags to not their images");
        }
    }

    @Override
    public Optional<TagLine> get(long id) {
        return Optional.ofNullable(tagLineDao.getById(id));
    }

    @Override
    @Transactional
    public void delete(Long id, User loggedUser) {
        TagLine tagLine = Optional.ofNullable(tagLineDao.getById(id))
                .orElseThrow(() -> new NotFoundException("Tagline with id=%s does not exist"));
        if (tagLine.getImage().getOwner().equals(loggedUser)) {
            tagLineDao.delete(id);
        } else {
            throw new NotAllowedActionException("Users can not delete tag from not their images");
        }
    }
}
