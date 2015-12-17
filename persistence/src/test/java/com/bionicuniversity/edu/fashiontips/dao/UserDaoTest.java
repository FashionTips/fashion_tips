package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Post;
import com.bionicuniversity.edu.fashiontips.entity.Role;
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
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test cases for UserDao.
 *
 * @author Alexander Laktionov
 * @author Maksym Dolia
 * @since 30/11/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("dev")
public class UserDaoTest {

    private static User user1 = new User("login4", "email4@example.com", "1111");
    private static Post post1 = new Post(user1, "title4", "How my glasses fits me?", Post.Category.QUESTION);
    private static User user2 = new User("login1", "email1@example.com", "1111");
    private static Post post2 = new Post(user2, "title1", "what fits me with these pants?", Post.Category.QUESTION);
    static {
        user1.setId(4L);
        user2.setId(1L);
    }

    @Inject
    private PostDao postDao;

    @Inject
    private UserDao userDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSaveUser() {
        User user = new User("login4", "email4@example.com", "1111");
        user = userDao.save(user);
        System.out.println(user);
        assertEquals(user1, userDao.getById(4L));
    }

    @Test
    public void testDeleteUser() {
        userDao.delete(1L);
        User user = userDao.getById(1L);
        assertNull(user);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setPassword("$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6");
        user.setEmail("email1@example.com");
        user.setLogin("login1");
        Role role = new Role("ROLE_USER");
        role.setId(1L);
        user.setRoles(Collections.singletonList(role));
        User expected = userDao.getById(1L);
        assertEquals(user.toString(), expected.toString());
    }

    @Test
    public void testFindByEmail_EmailExist() throws Exception {
        User user = userDao.getById(1L);

        assertEquals("Should find the same user by email.", 1L, userDao.findByEmail(user.getEmail()).getId().longValue());
    }

    @Test
    public void testFindByEmail_EmailDoesNotExist() throws Exception {
        String email = "hello@world.com";

        assertNull("Should find nothing due to email is not real.", userDao.findByEmail(email));
    }
}
