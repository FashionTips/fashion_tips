package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.HashSet;

/**
 * Class for testing PostService
 *
 */
public class ServiceTestData {
    public static final Post NOT_EXISTING_POST = new Post(-1L, new User(), "", "?????", Post.Category.POST, new HashSet<>());

}
