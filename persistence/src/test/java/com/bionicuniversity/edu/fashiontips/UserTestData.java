package com.bionicuniversity.edu.fashiontips;

import com.bionicuniversity.edu.fashiontips.entity.User;

/**
 * This class stores default and test values for User entity
 *
 * @author Volodymyr Portianko
 */
public class UserTestData {

    public static final Long USER1_ID = 1L;
    public static final Long USER2_ID = 2L;
    public static final Long USER3_ID = 3L;

    public static final User USER1 = new User(
            USER1_ID, "login1", "email1@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User USER2 = new User(
            USER2_ID, "login2", "email2@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
    public static final User USER3 = new User(
            USER3_ID, "login3", "email3@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");

}
