package com.bionic.edu;

import com.bionic.edu.matcher.ModelMatcher;
import com.bionic.edu.model.BaseEntity;
import com.bionic.edu.model.User;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by VPortianko on 06.11.2015.
 */
public class UserTestData {

    public static final ModelMatcher<User, String> MATCHER = new ModelMatcher<>(User::toString);

    public static final int USER1_ID = BaseEntity.START_SEQ;
    public static final int USER2_ID = BaseEntity.START_SEQ + 1;
    public static final int USER3_ID = BaseEntity.START_SEQ + 2;

    public static final User USER1 = new User(USER1_ID,"maks@gmail.com","password","Макс", LocalDateTime.of(2015, Month.NOVEMBER, 1, 6, 0));
    public static final User USER2 = new User(USER2_ID,"yulia@yandex.ru","password","Юля", LocalDateTime.of(2015, Month.NOVEMBER, 1, 7, 0));
    public static final User USER3 = new User(USER3_ID,"anya@ukr.net","password","Аня", LocalDateTime.of(2015, Month.NOVEMBER, 1, 8, 0));

}
