package com.bionicuniversity.edu.fashiontips.dao;

import com.bionicuniversity.edu.fashiontips.entity.Category;
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
 * Class for testing PostDao
 */

@ActiveProfiles("dev")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutHSQLDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class PostDaoTest {

    private static User user1 = new User("login4", "email4@example.com", "1111");
    private static Post post1 = new Post(user1, "title4", "How my glasses fits me?", Category.QUESTION);
    static {
        user1.setId(4L);
    }


    @Inject
    private PostDao postDao;

    @Inject
    private UserDao userDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAddPost() {
        User user = new User("login4", "email4@example.com", "1111");
        user = userDao.save(user);
        System.out.println(user);
        Post post = new Post(user, "title4", "How my glasses fits me?", Category.QUESTION);
        post = postDao.save(post);
        System.out.println(post);
        Post expected = postDao.getById(7L);
        post.setCreated(expected.getCreated());
        assertEquals(post.toString(), postDao.getById(7L).toString());
    }

    @Test
    public void testDeletePost() {
        postDao.delete(1L);
        Post post = postDao.getById(1L);
        assertNull(post);
    }

    @Test
    public void testGetPostById() {
        User user = userDao.getById(1L);
        Post post = new Post();
        post.setUser(user);
        post.setTitle("title1");
        post.setTextMessage("what fits me with these pants?");
        post.setCategory(Category.QUESTION);
        Post expected = postDao.getById(1L);
        post.setCreated(expected.getCreated());
        post.setId(1L);
        assertEquals(post.toString(), expected.toString());
    }
}
