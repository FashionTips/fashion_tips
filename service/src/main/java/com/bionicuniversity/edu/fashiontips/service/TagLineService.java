package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.*;

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
