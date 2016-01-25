package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
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
import javax.persistence.EntityExistsException;
import java.util.List;

import static com.bionicuniversity.edu.fashiontips.UserTestData.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

/**
 * Calss for testing UserDao
 */

@ActiveProfiles("postgres")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class UserDaoTest {
    @Inject
    private UserDao userDao;

    @Inject
    private VerificationTokenDao verificationTokenDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSaveUser() {
        User testUser = userDao.save(USER_MATCHER.deepClone(NEW_USER_BEFORE_SAVE));

        /*Check that the method Save returns correct value */
        assertReflectionEquals(NEW_USER_AFTER_SAVE, testUser,IGNORE_DEFAULTS);

        List<User> testList = userDao.getAll();
        /*Check that the new User added*/
        assertReflectionEquals(LIST_WITH_NEW_USER, testList,IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testDeleteUser() {
        /*Delete User(ID = 1) from DB*/
        userDao.delete(1L);
        List<User> testList = userDao.getAll();
        assertReflectionEquals(LIST_IF_DELETE_FIRST_USER, testList,IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testGetUserById() {
        User testUser = userDao.getById(1L);
        assertReflectionEquals(USER1, testUser,IGNORE_DEFAULTS);
    }

    @Test
    public void testGetAll() throws Exception {
        /*Get list of all Users from DB*/
        List<User> testList = userDao.getAll();
        assertReflectionEquals(LIST_OF_USERS, testList,IGNORE_DEFAULTS, LENIENT_ORDER);
    }

    @Test
    public void testGetByLogin() throws Exception {
        /*Get User(login = "login2") from DB*/
        User testUser = userDao.getByLogin("login2");
        assertReflectionEquals(USER2, testUser, IGNORE_DEFAULTS);
    }

    @Test
    public void testFindByEmail_EmailExist() throws Exception {
        assertReflectionEquals("Should find the same user by email.", USER1, userDao.findByEmail("email1@example.com"), IGNORE_DEFAULTS);
    }

    @Test
    public void testFindByEmail_EmailDoesNotExist() throws Exception {
        String email = "hello@world.com";
        assertNull("Should find nothing due to email is not real.", userDao.findByEmail(email));
    }

    @Test(expected = EntityExistsException.class)
    public void testGeneretionTokenWhenDuplicateEmailPresent() throws EntityExistsException {
        VerificationToken verificationToken =
                new VerificationToken("arusich2008@ukr.net",
                        "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
        VerificationToken verificationToken2 = new VerificationToken("arusich2008@ukr.net",
                "b36e992c2cc62c9f5f589e006862b2e5d7fa485b1d89840fc573f28551f86261");
        verificationTokenDao.save(verificationToken);
        verificationTokenDao.save(verificationToken2);
        fail();
    }
}
