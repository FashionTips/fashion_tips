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
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bionicuniversity.edu.fashiontips.ImageTestData.*;
import static com.bionicuniversity.edu.fashiontips.UserTestData.*;
import static org.junit.Assert.*;

/**
 * Class for testing PostDao
 */

@ActiveProfiles("production")
@ContextConfiguration("classpath:spring/spring-persistence.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/filloutDB.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(encoding = "UTF-8"))
public class PostDaoTest {

    private static User user1 = new User("login4", "email4@example.com", "1111");
    private static Post post1 = new Post(user1, "title4", "How my glasses fits me?", Post.Category.QUESTION);
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
    @Transactional
    public void testAddValidPost() {
        User user = new User("login4", "email4@example.com", "1111");
        user = userDao.save(user);
        System.out.println(user);
        Post post = new Post(user, "title4", "How my glasses fits me?", Post.Category.QUESTION);
        post.setImages(Arrays.asList(IMAGE4, IMAGE5));
        post.setLikedByUsers(Stream.of(USER1, USER2, USER3).collect(Collectors.toSet()));
        post = postDao.save(post);
        System.out.println(post);
        Post expected = postDao.getById(7L);
        post.setCreated(expected.getCreated());
        assertEquals(post.toString(), postDao.getById(7L).toString());
    }

    @Test
    public void testAddNotValidPost() {
        thrown.expect(ConstraintViolationException.class);
        Post post = new Post(user1, "", "", Post.Category.POST);
        postDao.save(post);
        fail("Should not save not valid entities.");
    }

    @Test
    public void testDeletePost() {
        postDao.delete(1L);
        Post post = postDao.getById(1L);
        assertNull(post);
    }

    @Test
    @Transactional
    public void testGetPostById() {
        User user = userDao.getById(1L);
        Post post = new Post();
        post.setUser(user);
        post.setTitle("title1");
        post.setTextMessage("what fits me with these pants?");
        post.setCategory(Post.Category.QUESTION);
        post.setImages(Arrays.asList(IMAGE1, IMAGE2, IMAGE3));
        post.setLikedByUsers(Stream.of(USER2,USER3).collect(Collectors.toSet()));
        Post expected = postDao.getById(1L);

        post.setCreated(expected.getCreated());
        post.setId(1L);
        assertEquals(post.toString(), expected.toString());
    }
}
