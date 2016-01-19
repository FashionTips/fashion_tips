package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Comment;
import com.bionicuniversity.edu.fashiontips.entity.Post;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static com.bionicuniversity.edu.fashiontips.ImageTestData.*;
import static com.bionicuniversity.edu.fashiontips.TagLineTestData.*;
import static com.bionicuniversity.edu.fashiontips.UserTestData.*;

/**
 * Created by Sergiy on 15.12.2015
 * Project: fashion-tips
 */
public class PostAndCommentTestData {
    public static final ModelMatcher<Post, String> POST_MATCHER = new ModelMatcher<>(Post::toString);
    public static final ModelMatcher<Comment, String> COMMENT_MATCHER = new ModelMatcher<>(Comment::toString);

    public static final Post POST1 = new Post(1L, USER1, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 0), "title1", "what fits me with these pants?", Post.Category.QUESTION, Arrays.asList(IMAGE1, IMAGE2, IMAGE3), new ArrayList<>(), new HashSet<>(Arrays.asList(USER2, USER3)), null, null, Post.Status.PUBLISHED);
    public static final Post POST2 = new Post(2L, USER2, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 1), "title2", "what hat does put on?", Post.Category.QUESTION, Arrays.asList(IMAGE4, IMAGE5), new ArrayList<>(), new HashSet<>(Arrays.asList(USER3)), null, null, Post.Status.PUBLISHED);
    public static final Post POST3 = new Post(3L, USER3, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 2), "title3", "red is cool?", Post.Category.QUESTION, Collections.singletonList(IMAGE6), new ArrayList<>(), new HashSet<>(Arrays.asList(USER1, USER2)), null, null, Post.Status.PUBLISHED);
    public static final Post POST4 = new Post(4L, USER1, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 3), "title1", "what fits me with these pants? Again", Post.Category.POST, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), null, null, Post.Status.PUBLISHED);
    public static final Post POST5 = new Post(5L, USER2, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 4), "title2", "what hat does put on? Again", Post.Category.POST, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), null, null, Post.Status.PUBLISHED);
    public static final Post POST6 = new Post(6L, USER3, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 5), "title3", "red is cool? Again", Post.Category.POST, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), null, null, Post.Status.HIDDEN);

    public static final Post POST3_AFTER_ADD_NEW_COMMENT = new Post(3L, USER3, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 2), "title3", "red is cool?", Post.Category.QUESTION, Collections.singletonList(IMAGE6), new ArrayList<>(), new HashSet<>(Arrays.asList(USER1, USER2)), null, null, Post.Status.PUBLISHED);
    public static final Post POST2_AFTER_UPDATE_COMMENT2 = new Post(2L, USER2, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 1), "title2", "what hat does put on?", Post.Category.QUESTION, Arrays.asList(IMAGE4, IMAGE5), new ArrayList<>(), new HashSet<>(Arrays.asList(USER3)), null, null, Post.Status.PUBLISHED);
    public static final Post NEW_POST_BEFORE_SAVE = new Post(null, USER1, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 20), "title_new", "How are you?", Post.Category.QUESTION, Arrays.asList(IMAGE4, IMAGE5), new ArrayList<>(), new HashSet<>(), null, null, Post.Status.PUBLISHED);
    public static final Post NEW_POST_AFTER_SAVE = new Post(7L, USER1, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 20), "title_new", "How are you?", Post.Category.QUESTION, Arrays.asList(IMAGE4, IMAGE5), new ArrayList<>(), new HashSet<>(), null, null, Post.Status.PUBLISHED);
    public static final Post UPDATE_POST1 = new Post(1L, USER1, POST1.getCreated(), "UPDATE title", "UPDATE what fits me with these pants?", Post.Category.QUESTION, Arrays.asList(IMAGE1, IMAGE2, IMAGE3), new ArrayList<>(), new HashSet<>(Arrays.asList(USER2, USER3)), null, null, Post.Status.PUBLISHED);
    public static final Post NOT_VALID_POST = new Post(USER1, "", "", Post.Category.POST);

    public static final Post HIDDEN_POST = new Post(7L, USER3, LocalDateTime.of(2015, Month.DECEMBER, 16, 12, 15), "hidden", "hidden. Again", Post.Category.POST, new ArrayList<>(), new ArrayList<>(), new HashSet<>(), null, null, Post.Status.HIDDEN);

    public static final List<Post> LIST_IF_DELETE_FIRST_POST = Arrays.asList(POST2, POST3, POST4, POST5, POST6);
    public static final List<Post> LIST_OF_POSTS = Arrays.asList(POST1, POST2, POST3, POST4, POST5, POST6);
    public static final List<Post> LIST_WITH_NEW_POST = Arrays.asList(POST1, POST2, POST3, POST4, POST5, POST6, NEW_POST_AFTER_SAVE);
    public static final List<Post> LIST_IF_UPDATE_FIRST_POST = Arrays.asList(UPDATE_POST1, POST2, POST3, POST4, POST5, POST6);
    public static final List<Post> FIND_BY_CATEGORY_QUESTION = Arrays.asList(POST3, POST2, POST1);
    public static final List<Post> FIND_BY_CATEGORY_POST = Arrays.asList(POST5, POST4);

    public static final List<Post> FIND_BY_USER3_SORTED_BY_CREATED = Arrays.asList(POST3);
    public static final List<Post> FIND_BY_USER3_WITH_HIDDEN_POST = Arrays.asList(HIDDEN_POST, POST6, POST3);

    public static final List<Post> FIND_BY_WORD_AGAIN_SORTED_BY_CREATED = Arrays.asList(POST5, POST4);
    public static final List<Post> FIND_ALL_SORTED_BY_CREATED = Arrays.asList(POST5, POST4, POST3, POST2, POST1);

    public static final Comment COMMENT1 = new Comment(1L, "cool!", POST1, USER1, LocalDateTime.of(2015, Month.DECEMBER, 17, 12, 0));
    public static final Comment COMMENT2 = new Comment(2L, "amazing!!", POST2, USER1, LocalDateTime.of(2015, Month.DECEMBER, 17, 12, 1));
    public static final Comment COMMENT3 = new Comment(3L, "perfect!!!", POST3, USER2, LocalDateTime.of(2015, Month.DECEMBER, 17, 12, 2));
    public static final Comment UPDATE_COMMENT2 = new Comment(2L, "UpdateFromTest", POST2_AFTER_UPDATE_COMMENT2, USER1, LocalDateTime.of(2015, Month.DECEMBER, 17, 12, 1));

    public static final Comment NEW_COMMENT_BEFORE_SAVE = new Comment(null, "terrible hat!!!", POST3_AFTER_ADD_NEW_COMMENT, USER2, LocalDateTime.of(2015, Month.DECEMBER, 17, 13, 2));
    public static final Comment NEW_COMMENT_AFTER_SAVE = new Comment(4L, "terrible hat!!!", POST3_AFTER_ADD_NEW_COMMENT, USER2, LocalDateTime.of(2015, Month.DECEMBER, 17, 13, 2));

    public static final Comment COMMENT3_AFTER_ADD_NEW_COMMENT = new Comment(3L, "perfect!!!", POST3_AFTER_ADD_NEW_COMMENT, USER2, LocalDateTime.of(2015, Month.DECEMBER, 17, 12, 2));

    public static final Comment COMMENT1_AFTER_UPDATE_POST1 = new Comment(1L, "cool!", UPDATE_POST1, USER1, LocalDateTime.of(2015, Month.DECEMBER, 17, 12, 0));

    public static final List<Comment> LIST_OF_COMMENTS = Arrays.asList(COMMENT1, COMMENT2, COMMENT3);
    public static final List<Comment> LIST_WITH_NEW_COMMENT = Arrays.asList(COMMENT1, COMMENT2, COMMENT3_AFTER_ADD_NEW_COMMENT, NEW_COMMENT_AFTER_SAVE);
    public static final List<Comment> LIST_IF_DELETE_FIRST_COMMENT = Arrays.asList(COMMENT2, COMMENT3);
    public static final List<Comment> LIST_IF_UPDATE_COMMENT2 = Arrays.asList(COMMENT1, UPDATE_COMMENT2, COMMENT3);

    static {
        POST1.setTagLines(Arrays.asList(TAG_LINE1, TAG_LINE2, TAG_LINE3));
        POST1.setComments(Arrays.asList(COMMENT1));
        POST2.setComments(Arrays.asList(COMMENT2));
        POST3.setComments(Arrays.asList(COMMENT3));

        POST3_AFTER_ADD_NEW_COMMENT.setComments(Arrays.asList(COMMENT3_AFTER_ADD_NEW_COMMENT, NEW_COMMENT_AFTER_SAVE));
        POST2_AFTER_UPDATE_COMMENT2.setComments(Arrays.asList(UPDATE_COMMENT2));

        UPDATE_POST1.setComments(Arrays.asList(COMMENT1_AFTER_UPDATE_POST1));
        UPDATE_POST1.setTagLines(Arrays.asList(UPDATED_TAG_LINE_FOR_POST, TAG_LINE2, TAG_LINE3));

        POST2.setTagLines(new ArrayList<>());
        POST3.setTagLines(new ArrayList<>());
        POST4.setTagLines(new ArrayList<>());
        POST5.setTagLines(new ArrayList<>());
        POST6.setTagLines(new ArrayList<>());

        NEW_POST_BEFORE_SAVE.setTagLines(Arrays.asList(NEW_TAG_LINE_FOR_POST));
        NEW_POST_AFTER_SAVE.setTagLines(Arrays.asList(ADDED_TAG_LINE_FOR_POST));
    }
}
