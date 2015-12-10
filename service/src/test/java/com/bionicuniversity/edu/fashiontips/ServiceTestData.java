package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for testing PostService
 * @author Sergiy
 */
public class ServiceTestData {
    /*existing tags*/
    public static final Tag TAG1 = new Tag(1L, "tag1");
    public static final Tag TAG2 = new Tag(2L, "tag2");
    public static final Tag TAG3 = new Tag(3L, "tag3");
    /*new tags*/
    public static final Tag TAG4 = new Tag(4L, "tag4");
    public static final Tag TAG5 = new Tag(5L, "tag5");

    public static final Set<Tag> TAGS = new HashSet<>(Arrays.asList(TAG1, TAG2, TAG3, TAG4, TAG5));

    public static final List<String> TAG_NAMES = Arrays.asList("tag1", "tag2", "tag3", "tag4", "tag5");

    public static final Post NOT_EXISTING_POST = new Post(-1L, new User(), "", "?????", Post.Category.POST, new HashSet<>(), new HashSet<>());

}
