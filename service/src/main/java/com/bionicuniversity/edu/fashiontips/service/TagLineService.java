package com.bionicuniversity.edu.fashiontips.service;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import com.bionicuniversity.edu.fashiontips.entity.TagType;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * TagLine Service Interface.
 *
 * @author Vadym Golub
 */
public interface TagLineService {

    List<Clothes> getAvailableTypeOfClothes();

    List<TagType> getExistingTagTypes();

    TagLine save(TagLine tagLine, Long imageId, User loggedUser);

    Optional<TagLine> get(long id);

    /*
    * Deletes tagline
    *
    * @param id TagLine id
    * @param loggedUser logged user entity
    * */
    void delete(Long id, User loggedUser);
}
