package com.bionicuniversity.edu.fashiontips.dao.impl;

import com.bionicuniversity.edu.fashiontips.dao.PostDao;
import com.bionicuniversity.edu.fashiontips.entity.Post;
import org.springframework.stereotype.Repository;

/**
 * Created by slav9nin on 25.11.2015.
 */

@Repository
public class PostDaoImpl extends GenericDaoImpl<Post, Long> implements PostDao {
}
