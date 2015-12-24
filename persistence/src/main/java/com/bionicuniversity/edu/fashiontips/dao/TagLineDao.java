package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;

import java.util.List;

/**
 * DAO interface to deal with {@code TagLine} entity.
 *
 * @author Vadym Golub
 */
public interface TagLineDao extends GenericDao<TagLine, Long> {

    List<TagLine> getAllByClothes(Clothes clothesTag);

}
