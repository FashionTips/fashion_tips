package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Post;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        User expected = userDao.getById(1L);
        assertEquals(user.toString(), expected.toString());
    }

//    @Test
//    public void testGetPostsOfUser() {
//        User user = userDao.getById(1L);
//        Post[] posts = new Post[2];
//        Post post1 = new Post(user2, "title1", "what fits me with these pants?", Category.QUESTION);
//        Post post2 = new Post(user2, "title1", "what fits me with these pants? Again", Category.POST);
//        post1.setId(user.getPosts().toArray(new Post[0])[0].getId());
//        post2.setId(user.getPosts().toArray(new Post[0])[1].getId());
//        posts[0] = post1;
//        posts[1] = post2;
//        assertArrayEquals(posts, user.getPosts().toArray(new Post[0]));
//    }
}
