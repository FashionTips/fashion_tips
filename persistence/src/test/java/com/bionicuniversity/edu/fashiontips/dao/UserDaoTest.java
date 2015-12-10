package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.DaoTestData.*;
import static org.junit.Assert.assertEquals;

/**
 * Calss for testing UserDao
 */

@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class UserDaoTest {

    @Inject
    private UserDao userDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSaveUser() {
        User testUser = new User("login4", "email4@example.com", "$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");

        testUser = userDao.save(testUser);

        /*Check that the method Save returns correct value */
        assertEquals(USER4.toString(), testUser.toString());

        List<User> testList = userDao.getAll();

        assertEquals(Arrays.asList(USER1, USER2, USER3, USER4).toString(), testList.toString());
    }

    @Test
    public void testDeleteUser() {
       /*Delete User(ID = 1) from DB*/
        userDao.delete(1L);

        List<User> testList = userDao.getAll();

        assertEquals(Arrays.asList(USER2, USER3).toString(), testList.toString());
    }

    @Test
    public void testGetUserById() {
        User testUser = userDao.getById(1L);

        assertEquals(USER1.toString(), testUser.toString());
    }

    @Test
    public void testGetAll() throws Exception {

        /*Get list of all Users from DB*/
        List<User> testList = userDao.getAll();

        assertEquals(USERS.toString(), testList.toString());
    }

    @Test
    public void testGetByLogin() throws Exception {
        /*Get User(login = "login2") from DB*/
        User testUser = userDao.getByLogin("login2");

        assertEquals(USER2.toString(), testUser.toString());
    }
}
