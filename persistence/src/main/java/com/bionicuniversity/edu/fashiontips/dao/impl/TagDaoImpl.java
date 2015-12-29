package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagDao;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of {@code TagDao} interface.
 *
 * @author Vadym Golub
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {
}
