package com.bionic.edu;

import com.bionic.edu.matcher.ModelMatcher;
import com.bionic.edu.model.BaseEntity;
import com.bionic.edu.model.Comment;

import java.time.LocalDateTime;
import java.time.Month;

import static com.bionic.edu.RequestTestData.*;
import static com.bionic.edu.UserTestData.*;

/**
 * Created by VPortianko on 09.11.2015.
 */
public class CommentTestData {

    public static final ModelMatcher<Comment, String> COMM_MATCHER = new ModelMatcher<>(Comment::toString);

    public static final int COMM1_1_ID = BaseEntity.START_SEQ + 7;
    public static final int COMM1_2_ID = BaseEntity.START_SEQ + 8;
    public static final int COMM2_1_ID = BaseEntity.START_SEQ + 9;
    public static final int COMM3_1_ID = BaseEntity.START_SEQ + 10;
    public static final int COMM3_2_ID = BaseEntity.START_SEQ + 11;
    public static final int COMM3_3_ID = BaseEntity.START_SEQ + 12;
    public static final int COMM4_1_ID = BaseEntity.START_SEQ + 13;

    public static final Comment COMM1_1 = new Comment(COMM1_1_ID, REQ1, USER2,
            "Классные туфли!",
            LocalDateTime.of(2015, Month.NOVEMBER,1,18,0));
    public static final Comment COMM1_2 = new Comment(COMM1_2_ID, REQ1, USER3,
            "С джинсами будет круто!!!",
            LocalDateTime.of(2015, Month.NOVEMBER,2,9,0));
    public static final Comment COMM2_1 = new Comment(COMM2_1_ID, REQ2, USER1,
            "Очень элегантная юбка.",
            LocalDateTime.of(2015, Month.NOVEMBER,1,19,0));
    public static final Comment COMM3_1 = new Comment(COMM3_1_ID, REQ3, USER3,
            "Классно на тебе сидит!!",
            LocalDateTime.of(2015, Month.NOVEMBER,2,17,0));
    public static final Comment COMM3_2 = new Comment(COMM3_2_ID, REQ3, USER2,
            "Спасибочки)))",
            LocalDateTime.of(2015, Month.NOVEMBER,3,11,0));
    public static final Comment COMM3_3 = new Comment(COMM3_3_ID, REQ3, USER1,
            "Тебе очень идет ;-)",
            LocalDateTime.of(2015, Month.NOVEMBER,3,12,0));
    public static final Comment COMM4_1 = new Comment(COMM4_1_ID, REQ4, USER2,
            "Очень стильно!",
            LocalDateTime.of(2015, Month.NOVEMBER,3,20,0));

}
