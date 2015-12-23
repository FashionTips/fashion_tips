package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.entity.TagType;

import java.util.List;

/**
 * TagLine Service Interface.
 *
 * @author Vadym Golub
 */
public interface TagLineService {

    List<Clothes> getAvailableTypeOfClothes();

    List<TagType> getExistingTagTypes();

    TagLine save(TagLine tagLine, long postId);

    TagLine get(long id);

}