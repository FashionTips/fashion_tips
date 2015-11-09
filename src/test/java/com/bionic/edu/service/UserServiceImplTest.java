package com.bionic.edu.service;

import com.bionic.edu.model.User;
import com.bionic.edu.util.exception.NotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.bionic.edu.UserTestData.*;

/**
 * Created by VPortianko on 06.11.2015.
 */
@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceImplTest {

    @Inject
    private UserService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSave() throws Exception {
        LocalDateTime newUserRegisteredTime = LocalDateTime.now();
        User newUser = new User(null,"new_email@gmail.com","123456","New User", newUserRegisteredTime);
        User createdUser = service.save(newUser);
        newUser.setId(createdUser.getId());
        USER_MATCHER.assertListEquals(Arrays.asList(newUser,USER3,USER1,USER2),service.getAll());
    }

    @Test
    public void testSaveDuplicateEmail() throws Exception {
        User newUser = new User(null,"maks@gmail.com","qwerty","New Maks", null);
        thrown.expect(DataAccessException.class);
        service.save(newUser);
    }

    @Test
    public void testUpdate() throws Exception {
        User updatedUser = new User(USER3);
        updatedUser.setName("Анютка");
        service.update(updatedUser);
        USER_MATCHER.assertEquals(updatedUser, service.get(USER3_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER1_ID);
        USER_MATCHER.assertListEquals(Arrays.asList(USER3, USER2), service.getAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(10);
    }

    @Test
    public void testGet() throws Exception {
        USER_MATCHER.assertEquals(USER2, service.get(USER2_ID));
    }

    @Test
    public void testGetByEmail() throws Exception {
        USER_MATCHER.assertEquals(USER3, service.getByEmail("anya@ukr.net"));
    }

    @Test
    public void testGetAll() throws Exception {
        USER_MATCHER.assertListEquals(Arrays.asList(USER3, USER1, USER2), service.getAll());
    }
}