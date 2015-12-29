package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Clothes;
import com.bionicuniversity.edu.fashiontips.entity.TagLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.PostAndCommentTestData.*;
import static com.bionicuniversity.edu.fashiontips.TagTestData.*;

/**
 * This class stores default and test values for TagLine entity
 *
 * @author Golub Vadym
 */
public class TagLineTestData {
    public static final ModelMatcher<TagLine, String> TAG_LINE_MATCHER = new ModelMatcher<>(TagLine::toString);

    public static final Clothes CLOTHES1 = new Clothes(3L, "Dresses");
    public static final Clothes CLOTHES2 = new Clothes(2L, "Cardigans");
    public static final Clothes CLOTHES3 = new Clothes(4L, "Intimates");

    public static final TagLine TAG_LINE1 = new TagLine(1L, null, CLOTHES1, Arrays.asList(TAG1, TAG2, TAG3));
    public static final TagLine TAG_LINE2 = new TagLine(2L, null, CLOTHES2, new ArrayList<>());
    public static final TagLine TAG_LINE3 = new TagLine(3L, null, CLOTHES3, new ArrayList<>());

    public static final TagLine NEW_TAG_LINE_BEFORE_SAVE = new TagLine(null, POST2, CLOTHES2, Arrays.asList(NEW_TAG_BEFORE_SAVE));
    public static final TagLine NEW_TAG_LINE_AFTER_SAVE = new TagLine(4L, POST2, CLOTHES2, Arrays.asList(NEW_TAG_AFTER_SAVE));
    public static final TagLine UPDATED_TAG_LINE = new TagLine(2L, null, CLOTHES1, LIST_OF_ADDED_TAGS);
    public static final TagLine UPDATED_TAG_LINE_FOR_POST = new TagLine(1L, null, CLOTHES1, LIST_WITH_UPDATED_FIRST_TAG);
    public static final TagLine NEW_TAG_LINE_FOR_POST = new TagLine(null, NEW_POST_BEFORE_SAVE, CLOTHES2, Arrays.asList(NEW_TAG_BEFORE_SAVE));
    public static final TagLine ADDED_TAG_LINE_FOR_POST = new TagLine(4L, NEW_POST_AFTER_SAVE, CLOTHES2, Arrays.asList(NEW_TAG_AFTER_SAVE));

    public static final List<TagLine> LIST_OF_TAG_LINES = Arrays.asList(TAG_LINE1, TAG_LINE2, TAG_LINE3);
    public static final List<TagLine> LIST_WITH_NEW_TAG_LINE = Arrays.asList(TAG_LINE1, TAG_LINE2, TAG_LINE3, NEW_TAG_LINE_AFTER_SAVE);

}
