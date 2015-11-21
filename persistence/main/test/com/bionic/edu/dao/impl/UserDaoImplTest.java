package com.bionic.edu.dao.impl;

import com.bionic.edu.dao.UserDao;
import com.bionic.edu.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by maxim on 11/18/15.
 */
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
//@Sql(scripts = "classpath:db/filloutDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserDaoImplTest {

    @Inject
    private UserDao userDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testGetById() throws Exception {
        User user = new User();
        user.setLogin("login1");
        user.setEmail("email1@example.com");
        user.setPassword("1111");
        user.setId(1L);
        assertEquals(user.toString(), userDao.getById(1L).toString());
    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}