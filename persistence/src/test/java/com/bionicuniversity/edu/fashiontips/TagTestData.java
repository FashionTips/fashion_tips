package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Tag;
import com.bionicuniversity.edu.fashiontips.entity.TagParameter;
import com.bionicuniversity.edu.fashiontips.entity.TagType;

import java.util.*;

/**
 * This class stores default and test values for Tag entity
 *
 * @author Golub Vadym
 */
public class TagTestData {
    public static final ModelMatcher<Tag, String> TAG_MATCHER = new ModelMatcher<>(Tag::toString);

    public static final TagType TAG_TYPE_BRAND = new TagType(1L, "brand");
    public static final TagType TAG_TYPE_STORE = new TagType(2L, "store");

    public static final TagParameter TAG_PARAMETER1 = new TagParameter(1L, "site", "http://untitledandco.com/collections/womens");
    public static final TagParameter TAG_PARAMETER2 = new TagParameter(2L, "url", "http://www.karmaloop.com/product/The-Hey-Ma-Tee-in-White/549232");
    public static final TagParameter TAG_PARAMETER3 = new TagParameter(3L, "site", "http://finchwear.com.ua/");

    public static final TagParameter NEW_TAG_PARAMETER = new TagParameter(null, "url", "google.com");
    public static final TagParameter ADDED_TAG_PARAMETER = new TagParameter(4L, "url", "google.com");

    public static final Tag TAG1 = new Tag(1L, TAG_TYPE_BRAND, Arrays.asList(TAG_PARAMETER1, TAG_PARAMETER2, TAG_PARAMETER3), "UNTITLED & CO");
    public static final Tag TAG2 = new Tag(2L, TAG_TYPE_STORE, new ArrayList<>(), "karmaloop");
    public static final Tag TAG3 = new Tag(3L, TAG_TYPE_BRAND, new ArrayList<>(), "Finch");

    public static final Tag NEW_TAG_BEFORE_SAVE = new Tag(null, TAG_TYPE_STORE, new ArrayList<>(), "Finch Shop");
    public static final Tag NEW_TAG_AFTER_SAVE = new Tag(4L, TAG_TYPE_STORE, new ArrayList<>(), "Finch Shop");
    public static final Tag UPDATED_TAG = new Tag(2L, TAG_TYPE_BRAND, Arrays.asList(new TagParameter(4L, "url", "google.com")), "test value");
    public static final Tag NEW_TAG_WITH_PARAMETERS = new Tag(null, TAG_TYPE_STORE, Arrays.asList(NEW_TAG_PARAMETER), "test tag value");
    public static final Tag ADDED_TAG_WITH_PARAMETERS = new Tag(5L, TAG_TYPE_STORE, Arrays.asList(ADDED_TAG_PARAMETER), "test tag value");


    public static final List<Tag> LIST_OF_TAGS = Arrays.asList(TAG1, TAG2, TAG3);
    public static final List<Tag> LIST_WITH_NEW_TAG = Arrays.asList(TAG1, TAG2, TAG3, NEW_TAG_AFTER_SAVE);
    public static final List<Tag> LIST_IF_DELETE_SCND_TAG = Arrays.asList(TAG1, TAG3);
    public static final List<Tag> LIST_WITH_UPDATED_SCND_TAG = Arrays.asList(TAG1, UPDATED_TAG, TAG3);
    public static final List<Tag> LIST_WITH_UPDATED_FIRST_TAG = Arrays.asList(TAG1, TAG2, TAG3);

    public static final List<Tag> LIST_OF_NEW_TAGS = Arrays.asList(NEW_TAG_BEFORE_SAVE, NEW_TAG_WITH_PARAMETERS);
    public static final List<Tag> LIST_OF_ADDED_TAGS = Arrays.asList(NEW_TAG_AFTER_SAVE, ADDED_TAG_WITH_PARAMETERS);
}
