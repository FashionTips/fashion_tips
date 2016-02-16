package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.TagLineDao;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of {@code TagLineDao} interface.
 *
 * @author Vadym Golub
 */
@Repository
public class TagLineDaoImpl extends GenericDaoImpl<TagLine, Long> implements TagLineDao {
}