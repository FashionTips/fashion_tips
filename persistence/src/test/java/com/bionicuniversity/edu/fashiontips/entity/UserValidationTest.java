package com.bionicuniversity.edu.fashiontips.entity;

import com.bionicuniversity.edu.fashiontips.annotation.Create;
import com.bionicuniversity.edu.fashiontips.annotation.Update;
import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Tests cases for the user's validation.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name = "parent", locations = "classpath:spring/spring-persistence.xml")
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("dev")
public class UserValidationTest {

    @Inject
    private Validator validator;

    @Inject
    private UserDao userDao;

    @Test
    public void testNotUniqueLoginWhenUserIsGoingToBeCreated() throws Exception {

        User user = new User(userDao.getById(1L).getLogin(), "some@email.com", "password");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, Create.class);

        assertEquals("Should be only one violation", 1, constraintViolations.size());

        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertEquals("Should belong to login field", "login", violation.getPropertyPath().toString());
        assertEquals("User with given login has already exist.", violation.getMessage());
    }

    @Test
    public void testNotUniqueLoginWhenUserIsGoingToBeUpdated() throws Exception {

        User user = new User(userDao.getById(1L).getLogin(), "some@email.com", "password");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, Update.class);

        assertEquals("Should not be any violations", 0, constraintViolations.size());
    }

    @Test
    public void testNotUniqueEmailWhenUserIsGoingToBeCreated() throws Exception {

        User user = new User("someLogin", userDao.getById(1L).getEmail(), "password");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, Create.class);

        assertEquals("Should be only one violation", 1, constraintViolations.size());

        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertEquals("Should belong to email field", "email", violation.getPropertyPath().toString());
        assertEquals("Email is already in use.", violation.getMessage());
    }

    @Test
    public void testNotUniqueEmailWhenUserIsGoingToBeUpdated() throws Exception {

        User user = new User("someLogin", userDao.getById(1L).getEmail(), "password");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, Update.class);

        assertEquals("Should not be any violations", 0, constraintViolations.size());
    }
}