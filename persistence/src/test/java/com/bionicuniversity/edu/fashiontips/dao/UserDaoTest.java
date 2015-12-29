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
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.UserTestData.*;
import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertEquals;

/**
 * Calss for testing UserDao
 */

@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class UserDaoTest {
    @Inject
    private UserDao userDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSaveUser() {
        User testUser = userDao.save(USER_MATCHER.deepClone(NEW_USER_BEFORE_SAVE));

        /*Check that the method Save returns correct value */
        USER_MATCHER.assertEquals(NEW_USER_AFTER_SAVE, testUser);

        List<User> testList = userDao.getAll();
        /*Check that the new User added*/
        USER_MATCHER.assertListEquals(LIST_WITH_NEW_USER, testList);
    }

    @Test
    public void testDeleteUser() {
        /*Delete User(ID = 1) from DB*/
        userDao.delete(1L);
        List<User> testList = userDao.getAll();
        USER_MATCHER.assertListEquals(LIST_IF_DELETE_FIRST_USER, testList);
    }

    @Test
    public void testGetUserById() {
        User testUser = userDao.getById(1L);
        USER_MATCHER.assertEquals(USER1, testUser);
    }

    @Test
    public void testGetAll() throws Exception {
        /*Get list of all Users from DB*/
        List<User> testList = userDao.getAll();
        USER_MATCHER.assertListEquals(LIST_OF_USERS, testList);
    }

    @Test
    public void testGetByLogin() throws Exception {
        /*Get User(login = "login2") from DB*/
        User testUser = userDao.getByLogin("login2");
        USER_MATCHER.assertEquals(USER2, testUser);
    }

    @Test
    public void testFindByEmail_EmailExist() throws Exception {
        USER_MATCHER.assertEquals("Should find the same user by email.", USER1, userDao.findByEmail("email1@example.com"));
    }

    @Test
    public void testFindByEmail_EmailDoesNotExist() throws Exception {
        String email = "hello@world.com";
        assertNull("Should find nothing due to email is not real.", userDao.findByEmail(email));
    }
}
