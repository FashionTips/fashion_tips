package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.Role;
import com.bionicuniversity.edu.fashiontips.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergiy on 15.12.2015
 * Project: fashion-tips
 */
public class UserTestData {
    public static final ModelMatcher<User, String> USER_MATCHER = new ModelMatcher<>(User::toString);

    public static final User USER1 = new User(1L, "login1", "email1@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6",Arrays.asList(new Role(1L,"ROLE_USER")));
    public static final User USER2 = new User(2L, "login2", "email2@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6",Arrays.asList(new Role(1L,"ROLE_USER")));
    public static final User USER3 = new User(3L, "login3", "email3@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6",Arrays.asList(new Role(1L,"ROLE_USER")));
    public static final User NEW_USER_BEFORE_SAVE = new User(null, "login4", "email4@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6",Arrays.asList(new Role(1L,"ROLE_USER")));
    public static final User NEW_USER_AFTER_SAVE = new User(4L, "login4", "email4@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6",Arrays.asList(new Role(1L,"ROLE_USER")));

    public static final List<User> LIST_OF_USERS = Arrays.asList(USER1, USER2, USER3);
    public static final List<User> LIST_WITH_NEW_USER = Arrays.asList(USER1, USER2, USER3, NEW_USER_AFTER_SAVE);
    public static final List<User> LIST_IF_DELETE_FIRST_USER = Arrays.asList(USER2, USER3);
}
